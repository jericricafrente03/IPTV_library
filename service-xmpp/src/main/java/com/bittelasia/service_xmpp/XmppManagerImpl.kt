package com.bittelasia.service_xmpp

import android.util.Log
import com.bittelasia.core_datastore.DataStoreOperations
import com.bittelasia.core_datastore.model.STB
import com.bittelasia.service_xmpp.extension.SimpleConnectionListener
import com.bittelasia.service_xmpp.extension.XmppManager
import com.bittelasia.service_xmpp.model.Account
import com.bittelasia.service_xmpp.model.AccountStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jivesoftware.smack.ConnectionConfiguration
import org.jivesoftware.smack.ReconnectionManager
import org.jivesoftware.smack.SASLAuthentication
import org.jivesoftware.smack.SmackException
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration
import javax.inject.Inject

private const val TAG = "MEME"

class XmppManagerImpl @Inject constructor(
    private val dataStore: DataStoreOperations
) : XmppManager {

    private var xmppConnection: XMPPTCPConnection? = null
    private var account: Account? = null
    private val connectionListener = SimpleConnectionListener()
    private lateinit var chatManager: ChatManager
    private val incomingMessageChannel = Channel<String>()
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun initialize() {
        if (xmppConnection == null) {
            dataStore.readStbFlow(Dispatchers.IO) {
                val username = it.USERNAME
                val password = it.PASSWORD
                if (username != "" && password != "") {
                    scope.launch {
                        login(
                            account = Account(
                                localPart = username,
                                password = password,
                                jid = "$username+@localhost",
                                domainPart = "localhost",
                                status = AccountStatus.Online
                            )
                        )
                    }
                }
            }
        }
    }

    override fun getConnection(): XMPPTCPConnection =
        xmppConnection ?: throw NoSuchElementException("Connection is not established.")

    override suspend fun login(account: Account) {
        this.account = account
        xmppConnection = account.login(
            configurationBuilder = ::getConfiguration,
            connectionBuilder = ::XMPPTCPConnection,
            connectionListener = ::addConnectionListener,
            successHandler = { account.connectionSuccessHandler(it) },
            failureHandler = { account.connectionFailureHandler(it) }
        )
    }

    private suspend fun Account.login(
        configurationBuilder: (Account) -> XMPPTCPConnectionConfiguration,
        connectionBuilder: (XMPPTCPConnectionConfiguration) -> XMPPTCPConnection,
        connectionListener: (XMPPTCPConnection) -> Unit,
        successHandler: suspend Account.(XMPPTCPConnection) -> XMPPTCPConnection,
        failureHandler: suspend Account.(Throwable?) -> Unit
    ): XMPPTCPConnection? {

        SASLAuthentication.blacklistSASLMechanism("SCRAM-SHA-1")
        SASLAuthentication.blacklistSASLMechanism("DIGEST-MD5")
        SASLAuthentication.unBlacklistSASLMechanism("PLAIN")

        val configuration = configurationBuilder(this)
        val connection = connectionBuilder(configuration)

        connectionListener(connection)
        val result = connection.connectAndLogin()

        return if (result.isSuccess) {
            successHandler(result.getOrThrow())
        } else {
            failureHandler(result.exceptionOrNull())
            null
        }
    }

    private fun getConfiguration(account: Account): XMPPTCPConnectionConfiguration =
        XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(account.localPart, account.password)
            .setXmppDomain(account.domainPart)
            .setHost(http(STB.HOST))
            .setPort(5222)
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .build()

    // connect and login are called with Dispatchers.IO context
    private suspend fun XMPPTCPConnection.connectAndLogin(): Result<XMPPTCPConnection> =
        runCatching {
            withContext(Dispatchers.IO) {
                connect()
                login()
                this@connectAndLogin
            }
        }

    private suspend fun Account.connectionSuccessHandler(
        connection: XMPPTCPConnection
    ): XMPPTCPConnection {
        if (connection.isAuthenticated) {
            configureReconnectionManager(connection)

            withContext(Dispatchers.IO){
                chatManager = ChatManager.getInstanceFor(connection)
                chatManager.addIncomingListener { _, message, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        incomingMessageChannel.send(message.body)
                    }
                }
            }
            AccountStatus.Online
        } else {
            AccountStatus.Unauthorized
        }
        return connection
    }

    override fun incomingMessage(): Flow<String> = incomingMessageChannel.receiveAsFlow()

    private fun Account.connectionFailureHandler(throwable: Throwable?) {
        when (throwable) {
            is SmackException.EndpointConnectionException -> {
                Log.v(TAG, "Connection Failure Server Not Found")
            }
            else -> {
                Log.v(TAG, "Connection Failure Unauthorized ${throwable?.message}")
            }
        }
    }

    private fun configureReconnectionManager(connection: XMPPTCPConnection) {
        val reconnectionManager = ReconnectionManager.getInstanceFor(connection)
        reconnectionManager.setReconnectionPolicy(ReconnectionManager.ReconnectionPolicy.FIXED_DELAY)
        reconnectionManager.setFixedDelay(5)
        reconnectionManager.enableAutomaticReconnection()
    }

    private fun addConnectionListener(connection: XMPPTCPConnection) {
        connection.addConnectionListener(connectionListener)
    }

    override fun onCleared() {
        xmppConnection?.removeConnectionListener(connectionListener)
    }

    private fun http(ipAddress: String): String {
        val regex = Regex("^http://")
        return ipAddress.replace(regex, "")
    }
}
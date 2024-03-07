package com.bittelasia.service_xmpp.extension

import com.bittelasia.service_xmpp.model.Account
import kotlinx.coroutines.flow.Flow
import org.jivesoftware.smack.tcp.XMPPTCPConnection

interface XmppManager {
    suspend fun initialize()
    fun getConnection(): XMPPTCPConnection
    suspend fun login(account: Account)
    fun incomingMessage(): Flow<String>
    fun onCleared()
}

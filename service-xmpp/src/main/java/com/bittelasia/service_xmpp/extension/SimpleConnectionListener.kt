package com.bittelasia.service_xmpp.extension

import android.util.Log
import org.jivesoftware.smack.ConnectionListener
import org.jivesoftware.smack.XMPPConnection

private const val TAG = "MEME"

class SimpleConnectionListener : ConnectionListener {
    override fun connecting(connection: XMPPConnection?) {
        Log.d(TAG, "connecting... (ConnectionListener)")
        super.connecting(connection)
    }

    override fun connected(connection: XMPPConnection?) {
        Log.d(TAG, "connected (ConnectionListener)")
        super.connected(connection)
    }

    override fun authenticated(connection: XMPPConnection?, resumed: Boolean) {
        Log.d(TAG, "authenticated (ConnectionListener), resumed: $resumed")
        super.authenticated(connection, resumed)
    }

    override fun connectionClosed() {
        Log.d(TAG, "connectionClosed (ConnectionListener)")
        super.connectionClosed()
    }

    override fun connectionClosedOnError(e: Exception?) {
        Log.d(TAG, "connectionClosedOnError (ConnectionListener) with error: $e")
        super.connectionClosedOnError(e)
    }
}
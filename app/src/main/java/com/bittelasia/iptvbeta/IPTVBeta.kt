package com.bittelasia.iptvbeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bittelasia.iptvbeta.navigation.auth.IPTVNavigation
import com.bittelasia.theme.IPTVBetaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IPTVBeta : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        ContextCompat.startForegroundService(this, Intent(this, XmppService::class.java))
        setContent {
            IPTVBetaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IPTVNavigation(rememberNavController())
                }
            }
        }
    }
}


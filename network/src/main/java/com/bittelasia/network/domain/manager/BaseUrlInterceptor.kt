package com.bittelasia.network.domain.manager

import android.util.Log
import com.bittelasia.core_datastore.DataStoreOperations
import com.bittelasia.core_datastore.model.STB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BaseUrlInterceptor @Inject constructor(pref: DataStoreOperations) : Interceptor {
    companion object {
        var newBaseUrl = STB.HOST + ":" + STB.PORT + "/"
        fun setBaseUrl(baseUrl: String) {
            newBaseUrl = baseUrl
        }
    }

    init {
        pref.readStbFlow(Dispatchers.IO) {
            newBaseUrl = it.HOST + ":" + it.PORT + "/"
        }
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url.newBuilder()
            .scheme(newBaseUrl.toHttpUrlOrNull()!!.scheme)
            .host(newBaseUrl.toHttpUrlOrNull()!!.host)
            .port(newBaseUrl.toHttpUrlOrNull()!!.port)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
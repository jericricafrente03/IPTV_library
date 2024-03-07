package com.bittelasia.service_xmpp.di

import com.bittelasia.core_datastore.DataStoreOperations
import com.bittelasia.service_xmpp.XmppManagerImpl
import com.bittelasia.service_xmpp.extension.XmppManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class XmppManagerModule {

    @Provides
    @Singleton
    fun providesXmppManager(
        dataStore: DataStoreOperations
    ): XmppManager {
        return XmppManagerImpl(
            dataStore = dataStore
        )
    }
}

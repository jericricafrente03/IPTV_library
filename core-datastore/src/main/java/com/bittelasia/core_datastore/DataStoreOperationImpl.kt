package com.bittelasia.core_datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.bittelasia.core_datastore.model.STB
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.stbDataStore: DataStore<Preferences> by preferencesDataStore(name = "stb")

class DataStoreOperationImpl(context: Context) : DataStoreOperations {
    private val dataStore = context.stbDataStore

    private object PreferencesKey {
        val HOST = stringPreferencesKey("HTTPPreference_HOST")
        val ROOM = stringPreferencesKey("APIKeyPreference_PREF_ROOM")
        val PORT = stringPreferencesKey("HTTPPreference_PORT")
        val FIRST_RUN = stringPreferencesKey("firstrun")
        val MAC_ADDRESS = stringPreferencesKey("dev_id")
        val API_KEY = stringPreferencesKey("api_key")
        val USERNAME = stringPreferencesKey("XMPPPreference_USERNAME")
        val PASSWORD = stringPreferencesKey("XMPPPreference_PASSWORD")
        val END_DATE = stringPreferencesKey("END_DATE")
        val REMAINING_DAYS = stringPreferencesKey("REMAINING_DAYS")
    }

    override suspend fun saveStbState(stb: STB) {
        dataStore.edit { datastore ->
            stb.apply {
                datastore[PreferencesKey.ROOM] = ROOM
                datastore[PreferencesKey.HOST] = HOST
                datastore[PreferencesKey.PORT] = PORT
                datastore[PreferencesKey.FIRST_RUN] = FIRST_RUN
                datastore[PreferencesKey.MAC_ADDRESS] = MAC_ADDRESS
                datastore[PreferencesKey.API_KEY] = API_KEY
                datastore[PreferencesKey.USERNAME] = USERNAME
                datastore[PreferencesKey.PASSWORD] = PASSWORD
                datastore[PreferencesKey.END_DATE] = END_DATE
                datastore[PreferencesKey.REMAINING_DAYS] = REMAINING_DAYS
            }
        }
    }

    override fun readStbState(callback: (Flow<STB>) -> Unit) {
        callback.invoke(dataStore.data.map { pref ->
            STB.apply {
                ROOM = pref[PreferencesKey.ROOM] ?: ""
                HOST = pref[PreferencesKey.HOST] ?: "http://127.0.0.1"
                PORT = pref[PreferencesKey.PORT] ?: ""
                FIRST_RUN = pref[PreferencesKey.FIRST_RUN] ?: ""
                MAC_ADDRESS = pref[PreferencesKey.MAC_ADDRESS] ?: ""
                API_KEY = pref[PreferencesKey.API_KEY] ?: ""
                USERNAME = pref[PreferencesKey.USERNAME] ?: ""
                PASSWORD = pref[PreferencesKey.PASSWORD] ?: ""
                END_DATE = pref[PreferencesKey.END_DATE] ?: ""
                REMAINING_DAYS = pref[PreferencesKey.REMAINING_DAYS] ?: ""
            }
        })
    }


    override fun readStbFlow(
        dispatcher: CoroutineDispatcher,
        callback: (STB) -> Unit
    ) {
        readStbState { flow ->
            CoroutineScope(dispatcher).launch {
                flow.collect { stb ->
                    callback.invoke(stb)
                }
            }
        }
    }
}
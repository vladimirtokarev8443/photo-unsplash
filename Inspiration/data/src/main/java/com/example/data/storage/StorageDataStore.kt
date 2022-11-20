package com.example.data.storage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATA_STORE_NAME = "data_store_name"
val VERIFICATION_STATUS = "verification_value"
val ACCESS_TOKEN = "access_token"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)

class StorageDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    suspend fun saveValueString(value: String, key: String) {
        context.dataStore.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun getValueString(key: String): Flow<String?> {
        return context.dataStore.data
            .map {
                it[stringPreferencesKey(key)]
            }
    }

}
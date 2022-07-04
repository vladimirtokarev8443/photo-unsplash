package com.example.inspiration.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.inspiration.data.enum.Verification
import com.example.inspiration.data.models.UserVerification
import com.example.inspiration.data.storage.datastore.AccessTokenRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val DATA_STORE_NAME = "data_store_name"
private val VERIFICATION_VALUE = stringPreferencesKey("verification_value")
private val ACCESS_TOKEN = stringPreferencesKey("access_token")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DATA_STORE_NAME)


class StorageDataStore @Inject constructor(
    @ApplicationContext private val context: Context
): UserVerificationRepository, AccessTokenRepository {

    override suspend fun saveUserVerification(userVerification: UserVerification) {
        context.dataStore.edit {
            it[VERIFICATION_VALUE] = userVerification.verificationValue.name
        }
    }

    override suspend fun getUserVerification(): Flow<UserVerification> {
        return context.dataStore.data
            .map {
                val verificationValue = it[VERIFICATION_VALUE] ?: Verification.FIRST_VIZIT.name

                UserVerification(
                    verificationValue = Verification.valueOf(verificationValue)
                )
            }
    }

    override suspend fun saveAccessToken(accessToken: String) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken
        }
    }

    override suspend fun getAccessToken(): Flow<String?> {
        return context.dataStore.data
            .map {
                it[ACCESS_TOKEN]
            }
    }

}
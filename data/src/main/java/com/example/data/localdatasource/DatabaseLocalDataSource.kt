package com.example.data.localdatasource

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.localdatasource.database.DatabaseConnection
import com.example.domain.models.UserContact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val USER_PREFERENCES_NAME = "user_preferences"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = USER_PREFERENCES_NAME
)

class DatabaseLocalDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : IDatabaseLocalDataSource {

    // At the top level of your kotlin file:

    object UserPreferencesKeys {
        val NAME = stringPreferencesKey("name")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        val PROFILE_PICTURE = stringPreferencesKey("profilePicture")
        val STATUS = booleanPreferencesKey("status")
        val OWNER = booleanPreferencesKey("owner")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged")
        val PASSWORD = stringPreferencesKey("password")
    }

    // Función para almacenar un valor en DataStore
    suspend fun saveUser(user: UserContact): Boolean {
        try {

            dataStore.edit { preferences ->
                preferences[UserPreferencesKeys.NAME] = user.name
                preferences[UserPreferencesKeys.PHONE_NUMBER] = user.phoneNumber
                preferences[UserPreferencesKeys.PROFILE_PICTURE] = user.profilePicture.toString()
                preferences[UserPreferencesKeys.STATUS] = user.status ?: true
                preferences[UserPreferencesKeys.OWNER] = user.owner ?: false
                preferences[UserPreferencesKeys.IS_LOGGED_IN] = true
                preferences[UserPreferencesKeys.PASSWORD] = "123345"

            }
            return true
        } catch (e: Exception) {
            return false
            Log.e("DataStore", "Error: " + e.message)
        }


    }

    val userFlow: Flow<UserContact?>
        get() = dataStore.data.map { preferences ->
            if (preferences.contains(UserPreferencesKeys.IS_LOGGED_IN)) {
                UserContact(
                    name = preferences[UserPreferencesKeys.NAME].orEmpty(),
                    phoneNumber = preferences[UserPreferencesKeys.PHONE_NUMBER].orEmpty(),
                    profilePicture = preferences[UserPreferencesKeys.PROFILE_PICTURE].orEmpty(),
                    status = preferences[UserPreferencesKeys.STATUS] ?: true,
                    owner = preferences[UserPreferencesKeys.OWNER] ?: false,

                    )
            } else {
                null
            }
        }

    val isLoggedInFlow: Flow<Boolean>
        get() = dataStore.data.map { preferences ->
            preferences[UserPreferencesKeys.IS_LOGGED_IN] ?: false
        }

    // Función para recuperar un valor de DataStore
    fun getValue() = dataStore.data.map {
        UserContact(
            name = it[UserPreferencesKeys.NAME].orEmpty(),
            phoneNumber = it[UserPreferencesKeys.PHONE_NUMBER].orEmpty(),
            profilePicture = it[UserPreferencesKeys.PROFILE_PICTURE].orEmpty(),
            status = it[UserPreferencesKeys.STATUS] ?: true,
            owner = it[UserPreferencesKeys.OWNER] ?: false
        )

    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[UserPreferencesKeys.IS_LOGGED_IN] = false
        }
    }

    suspend fun login(username: String, password: String): Flow<UserContact>? {
        val storedUsername = dataStore.data.first()[UserPreferencesKeys.NAME]
        val storedPassword = dataStore.data.first()[UserPreferencesKeys.PASSWORD]

        if (username == storedUsername && password == storedPassword) {
            dataStore.edit { preferences ->
                preferences[UserPreferencesKeys.IS_LOGGED_IN] = true
            }
            Log.d("DataStore", "Logged: $username")

            return dataStore.data.map {
                UserContact(
                    name = it[UserPreferencesKeys.NAME].orEmpty(),
                    phoneNumber = it[UserPreferencesKeys.PHONE_NUMBER].orEmpty(),
                    profilePicture = it[UserPreferencesKeys.PROFILE_PICTURE].orEmpty(),
                    status = it[UserPreferencesKeys.STATUS] ?: true,
                    owner = it[UserPreferencesKeys.OWNER] ?: false
                )
            }
        } else {
            Log.d("DataStore", "No Logged: $username")
            logout()
        }
        return null
    }

}
package data.local.setting

import kotlinx.coroutines.flow.Flow

interface AuthSettings {

    fun getAccessToken(): Flow<String>
    suspend fun setAccessToken(token: String)

    fun isLoggedIn(): Flow<Boolean>
    suspend fun setLoggedIn(b: Boolean)

    suspend fun setBaseUrl(baseUrl: String)
    fun getBaseUrl(): Flow<String>

    suspend fun setRefreshToken(token: String)
    fun getRefreshToken(): Flow<String>

    fun getName(): Flow<String>
    suspend fun setName(name: String)

    fun getRoleName(): Flow<String>
    suspend fun setRoleName(role: String)

    fun getHasSubordinate(): Flow<Boolean>
    suspend fun setHasSubordinate(b: Boolean)

    fun getPersonCode(): Flow<String>
    suspend fun setPersonCode(code: String?)

    fun getPersonId(): Flow<String>
    suspend fun setPersonId(id: String)


    fun getWorkplacesLastModifiedTime(): Flow<String>
    suspend fun setWorkplacesLastModifiedTime(time: String)

    fun getDeviceToken(): Flow<String>
    suspend fun setDeviceToken(token: String)

    fun getAddDeviceInfoCalled(): Flow<Boolean>
    suspend fun setAddDeviceInfoCalled(value: Boolean)
}

package data.local.setting

import util.helper.CommonFlow
import util.helper.asCommonFlow
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class AuthSettingsImpl  @OptIn(ExperimentalSettingsApi::class) constructor(
    private val settings: FlowSettings
) : AuthSettings {

    override fun getAccessToken(): Flow<String> {
        return settings.getStringFlow(ACCESS_TOKEN, "")
    }

    override suspend fun setAccessToken(token: String) {
        settings.putString(ACCESS_TOKEN, token)
    }

    override fun getRefreshToken(): Flow<String> {
        return settings.getStringFlow(REFRESH_TOKEN, "")
    }

    override fun getName(): Flow<String> {
        return settings.getStringFlow(NAME, "")
    }

    override suspend fun setName(name: String) {
        settings.putString(NAME, name)
    }

    override fun getRoleName(): Flow<String> {
        return settings.getStringFlow(ROLE_NAME, "")
    }

    override suspend fun setRoleName(role: String) {
        settings.putString(ROLE_NAME, role)
    }

    override fun getHasSubordinate(): Flow<Boolean> {
        return settings.getBooleanFlow(HAS_SUBORDINATE, false)
    }

    override suspend fun setHasSubordinate(b: Boolean) {
        settings.putBoolean(HAS_SUBORDINATE, b)
    }

    override fun getPersonCode(): Flow<String> {
        return settings.getStringFlow(PERSON_CODE, "")
    }

    override suspend fun setPersonCode(code: String?) {
        settings.putString(PERSON_CODE, code ?: "")
    }

    override fun getPersonId(): Flow<String> {
        return settings.getStringFlow(PERSON_ID, "")
    }

    override suspend fun setPersonId(id: String) {
        settings.putString(PERSON_ID, id)
    }

    override fun getWorkplacesLastModifiedTime(): Flow<String> {
        return settings.getStringFlow(WORKPLACE_LAST_MODIFIED_TIME, "")
    }

    override suspend fun setWorkplacesLastModifiedTime(time: String) {
        settings.putString(WORKPLACE_LAST_MODIFIED_TIME, time)
    }

    override fun getDeviceToken(): Flow<String> {
        return settings.getStringFlow(DEVICE_TOKEN, "")
    }

    override suspend fun setDeviceToken(token: String) {
        settings.putString(DEVICE_TOKEN, token)
    }

    override fun getAddDeviceInfoCalled(): Flow<Boolean> {
        return settings.getBooleanFlow("addDeviceInfoCalled", false)
    }

    override suspend fun setAddDeviceInfoCalled(value: Boolean) {
        return settings.putBoolean("addDeviceInfoCalled", value)
    }

    override suspend fun setRefreshToken(token: String) {
        settings.putString(REFRESH_TOKEN, token)
    }

    override fun isLoggedIn(): CommonFlow<Boolean> {
        return settings.getBooleanFlow(IS_LOGGED_IN, false).asCommonFlow()
    }

    override suspend fun setLoggedIn(b: Boolean) {
        settings.putBoolean(IS_LOGGED_IN, b)
    }

    override suspend fun setBaseUrl(baseUrl: String) {
        settings.putString(BASE_URL, baseUrl)
    }

    override fun getBaseUrl(): Flow<String> {
        return settings.getStringFlow(BASE_URL, "182.105.239.123:8080/api")
    }

    private companion object {
        private const val BASE_URL = "BASE_URL"
        private const val ACCESS_TOKEN = "ACCESS_TOKEN"
        private const val IS_LOGGED_IN = "IS_LOGGED_IN"
        private const val REFRESH_TOKEN = "REFRESH_TOKEN"
        private const val USERNAME = "USERNAME"
        private const val PASSWORD = "PASSWORD"
        private const val LOCALE = "LOCALE"
        private const val LOCALE_NAME = "LOCALE_NAME"
        private const val NAME = "NAME"
        private const val PERSON_CODE = "PERSON_CODE"
        private const val PERSON_ID = "PERSON_ID"
        private const val ROLE_NAME = "ROLE_NAME"
        private const val HAS_SUBORDINATE = "HAS_SUBORDINATE"
        private const val WORKPLACE_LAST_MODIFIED_TIME = "WORKPLACE_LAST_MODIFIED_TIME"
        private const val DEVICE_TOKEN = "DEVICE_TOKEN"
    }
}
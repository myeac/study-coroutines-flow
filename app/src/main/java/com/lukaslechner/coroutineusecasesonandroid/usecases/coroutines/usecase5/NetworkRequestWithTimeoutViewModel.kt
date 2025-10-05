package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase5

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import timber.log.Timber

class NetworkRequestWithTimeoutViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest(timeout: Long) {
        uiState.value = UiState.Loading
//        usingTimeOut(timeout)
        usingTimeOutOrNull(timeout)
    }

    private fun usingTimeOut(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersion = withTimeout(timeout) {
                    api.getRecentAndroidVersions()
                }
                uiState.value = UiState.Success(recentAndroidVersion)
            } catch (timeOut: TimeoutCancellationException) {
                uiState.value = UiState.Error("error Timeout en la solicitacion!!")
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("error en la solicitacion!")
            }
        }
    }

    private fun usingTimeOutOrNull(timeout: Long) {
        viewModelScope.launch {
            try {
                val recentAndroidVersion = withTimeoutOrNull(timeout) {
                    api.getRecentAndroidVersions()
                }
                if (recentAndroidVersion != null) {
                    uiState.value = UiState.Success(recentAndroidVersion)
                } else {
                    uiState.value = UiState.Error("error en la solicitacion!")
                }
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("error en la solicitacion!")
            }
        }
    }
}
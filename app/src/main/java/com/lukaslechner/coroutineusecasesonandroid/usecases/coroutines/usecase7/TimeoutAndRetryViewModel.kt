package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase7

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import timber.log.Timber

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoVersionDeferred = viewModelScope.async {
            retryWithTimeOut(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }

        val pieVersionDeferred = viewModelScope.async {
            retryWithTimeOut(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(
                    oreoVersionDeferred,
                    pieVersionDeferred,
                ).awaitAll()
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("error en la comunicacion servidor!!")
            }
        }
    }
}

suspend fun <T> retryWithTimeOut(
    numberOfTries: Int,
    timeout: Long,
    block: suspend () -> T
) = retry(numberOfTries) {
    withTimeout(timeout) {
        block()
    }
}

suspend fun <T> retry(
    numberOfTries: Int,
    delayBetweenRetries: Long = 100,
    block: suspend () -> T
): T {
    repeat(numberOfTries) {
        try {
            return block()
        } catch (e: Exception) {
            Timber.e(e)
        }
        delay(delayBetweenRetries)
    }
    return block()
}
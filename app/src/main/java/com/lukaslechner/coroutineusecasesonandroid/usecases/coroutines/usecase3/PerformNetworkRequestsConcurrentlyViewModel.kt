package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase3

import androidx.lifecycle.viewModelScope
import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber

class PerformNetworkRequestsConcurrentlyViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequestsSequentially() {
        //demora aproximadament 3 seg en ejecutar
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val oreoFeature = mockApi.getAndroidVersionFeatures(27)
                val pieFeature = mockApi.getAndroidVersionFeatures(28)
                val android10Feature = mockApi.getAndroidVersionFeatures(29)

                val versionFeatures = listOf(oreoFeature, pieFeature, android10Feature)
                uiState.value = UiState.Success(versionFeatures)
            } catch (e: Exception) {
                Timber.e(e)
                uiState.value = UiState.Error("falla en la conexion")
            }
        }
    }

    fun performNetworkRequestsConcurrently() {
        //demora aproximadament 3 seg en ejecutar
        uiState.value = UiState.Loading
        val oreoFeatureDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(27)
        }
        val pieFeatureDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(28)
        }
        val android10FeatureDeferred = viewModelScope.async {
            mockApi.getAndroidVersionFeatures(29)
        }
        try {
            viewModelScope.launch {
                val oreoFeatures = oreoFeatureDeferred.await()
                val pieFeature = pieFeatureDeferred.await()
                val android10Feature = android10FeatureDeferred.await()

                val versionFeatures = listOf(oreoFeatures, pieFeature, android10Feature)
                //or
                val versionFeaturesAwait = awaitAll(oreoFeatureDeferred, pieFeatureDeferred, android10FeatureDeferred)

                uiState.value = UiState.Success(versionFeaturesAwait)
            }
        } catch (e: Exception) {
            uiState.value = UiState.Error("Error en la comunicacion servidor")
        }


    }
}
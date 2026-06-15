package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase2.callbacks

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.AndroidVersion
import com.lukaslechner.coroutineusecasesonandroid.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    private var getAndroidVersionsCall: Call<List<AndroidVersion>>? = null
    private var getAndroidFeaturesCall: Call<VersionFeatures>? = null

    fun perform2SequentialNetworkRequest() {
        uiState.value = UiState.Loading

        getAndroidVersionsCall = mockApi.getRecentAndroidVersions()
        getAndroidVersionsCall?.enqueue(object : Callback<List<AndroidVersion>> {
            override fun onResponse(
                call: Call<List<AndroidVersion>?>,
                response: Response<List<AndroidVersion>?>
            ) {
                if (response.isSuccessful) {
                    val mostRecentVersions = response.body()!!.last()
                    getAndroidFeaturesCall = mockApi.getAndroidVersionFeatures(mostRecentVersions.apiLevel)
                    getAndroidFeaturesCall?.enqueue(object : Callback<VersionFeatures> {
                        override fun onResponse(
                            call: Call<VersionFeatures?>,
                            response: Response<VersionFeatures?>
                        ) {
                            if (response.isSuccessful) {
                                val featuresOfMostRecentVersions = response.body()!!
                                uiState.value = UiState.Success(featuresOfMostRecentVersions)
                            } else {
                                uiState.value = UiState.Error("Error en la respuesta de las Novedades de Version: ${mostRecentVersions.apiLevel}!")
                            }
                        }
                        override fun onFailure(
                            call: Call<VersionFeatures?>,
                            response: Throwable
                        ) {
                            uiState.value = UiState.Error("Algo inesperado ocurrio!")
                        }
                    })
                } else {
                    uiState.value = UiState.Error("Error en la respuesta!")
                }
            }

            override fun onFailure(
                call: Call<List<AndroidVersion>?>,
                response: Throwable
            ) {
                uiState.value = UiState.Error("Algo inesperado ocurrio!")
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getAndroidFeaturesCall?.cancel()
        getAndroidVersionsCall?.cancel()
    }
}
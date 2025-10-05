package com.lukaslechner.coroutineusecasesonandroid.usecases.coroutines.usecase1

import com.lukaslechner.coroutineusecasesonandroid.base.BaseViewModel
import com.lukaslechner.coroutineusecasesonandroid.mock.MockApi

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        //alternar entre cualquiera de estos estados
        uiState.value = UiState.Loading

        uiState.value = UiState.Error("Algo malo paso!!! :c")

        uiState.value= UiState.Success(listOf())
    }
}
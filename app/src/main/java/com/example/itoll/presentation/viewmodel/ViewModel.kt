package com.example.itoll.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.usecase.GetUseCase
import com.example.itoll.presentation.ConsumableValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val useCase: GetUseCase) : ViewModel() {

    private val _rocket: MutableLiveData<ConsumableValue<List<String>>> = MutableLiveData()
    val rocket: LiveData<ConsumableValue<List<String>>> = _rocket


    private val _loading: MutableLiveData<ConsumableValue<Boolean>> = MutableLiveData()
    val loading: LiveData<ConsumableValue<Boolean>> = _loading

    private val _failure: MutableLiveData<ConsumableValue<String>> = MutableLiveData()
    val failure: LiveData<ConsumableValue<String>> = _failure

    private var _error: MutableLiveData<ConsumableValue<Throwable>> = MutableLiveData()
    val error: LiveData<ConsumableValue<Throwable>> = _error

    fun getData() {
        viewModelScope.launch {
            Log.d("Viewmodel", "Call View Model Get***")

            _loading.postValue(ConsumableValue(true))
            when (val result = useCase.execute(Unit)) {
                is ResultData.Success -> {

                }

                is ResultData.Failure -> {

                }

                is ResultData.Error -> {

                }
            }

            _loading.postValue(ConsumableValue(false))

        }
    }
}
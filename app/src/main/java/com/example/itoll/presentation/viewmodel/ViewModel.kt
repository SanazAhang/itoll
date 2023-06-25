package com.example.itoll.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.usecase.GetUsersUseCase
import com.example.itoll.presentation.ConsumableValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val userUseCase: GetUsersUseCase) : ViewModel() {

    private val _Users: MutableLiveData<ConsumableValue<List<UserModel>>> = MutableLiveData()
    val users: LiveData<ConsumableValue<List<UserModel>>> = _Users


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
            when (val result = userUseCase.execute(Unit)) {
                is ResultData.Success -> {
                    _Users.postValue(ConsumableValue(result.value))
                }

                is ResultData.Failure -> {
                    _failure.postValue(ConsumableValue(result.message))
                }

                is ResultData.Error -> {
                    _error.postValue(ConsumableValue(result.throws))
                }
            }

            _loading.postValue(ConsumableValue(false))

        }
    }
}
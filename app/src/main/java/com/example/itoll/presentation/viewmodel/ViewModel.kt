package com.example.itoll.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.itoll.domain.model.FunctionName
import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.usecase.GetUserWithUserNameUseCase
import com.example.itoll.domain.usecase.GetUsersUseCase
import com.example.itoll.domain.usecase.GetUsersWithSearchUseCase
import com.example.itoll.presentation.ConsumableValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val getUserUseCase: GetUsersUseCase,
    private val getUserWithUserNameUseCase: GetUserWithUserNameUseCase,
    private val getUserWithsearchUseCase: GetUsersWithSearchUseCase
) : ViewModel() {


    var lastFunctionCall = FunctionName.USERS
    private val _users: MutableLiveData<List<UserModel>?> = MutableLiveData()
    val users: LiveData<List<UserModel>?> = _users

    private val _user: MutableLiveData<UserModel> = MutableLiveData()
    val user: LiveData<UserModel> = _user


    private val _loading: MutableLiveData<ConsumableValue<Boolean>> = MutableLiveData()
    val loading: LiveData<ConsumableValue<Boolean>> = _loading

    private val _failure: MutableLiveData<ConsumableValue<String>> = MutableLiveData()
    val failure: LiveData<ConsumableValue<String>> = _failure

    private var _error: MutableLiveData<ConsumableValue<Throwable>> = MutableLiveData()
    val error: LiveData<ConsumableValue<Throwable>> = _error

    fun getUsers() {
        lastFunctionCall = FunctionName.USERS
        viewModelScope.launch {
            _loading.postValue(ConsumableValue(true))

            when (val result = getUserUseCase.execute(Unit)) {
                is ResultData.Success -> {
                    result.value.let {

                        _users.postValue(it)
                    }
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

    fun getUser(userName: String) {
        viewModelScope.launch {
            _loading.postValue(ConsumableValue(true))

            when (val result = getUserWithUserNameUseCase.execute(userName)) {
                is ResultData.Success -> {
                    result.value.let {
                        _users.postValue(null)
                        _user.postValue(it)
                    }
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

    fun searchUser(textSearxh: String) {
        lastFunctionCall = FunctionName.SEARCH
        viewModelScope.launch {
            _loading.postValue(ConsumableValue(true))

            when (val result = getUserWithsearchUseCase.execute(textSearxh)) {
                is ResultData.Success -> {
                    result.value.let {
                        _users.postValue(null)
                        _users.postValue(it)
                    }
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
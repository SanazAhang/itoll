package com.example.itoll.domain.repository

import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel

interface UserRepository {
    suspend fun getUsers():ResultData<List<UserModel>>

    suspend fun getUser(userName:String):ResultData<UserModel>
}
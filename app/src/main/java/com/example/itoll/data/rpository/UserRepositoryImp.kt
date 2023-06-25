package com.example.itoll.data.rpository

import com.example.itoll.data.api.UserApi
import com.example.itoll.data.mapper.execute
import com.example.itoll.data.mapper.mapToModel
import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.model.map
import com.example.itoll.domain.repository.UserRepository

class UserRepositoryImp(private val userApi: UserApi) : UserRepository {

    override suspend fun getUsers(): ResultData<List<UserModel>> = execute {
        userApi.getUsers()
    }.map { users ->
        users.map { user ->
            user
                .mapToModel()
        }
    }

}
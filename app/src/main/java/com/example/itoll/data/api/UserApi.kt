package com.example.itoll.data.api

import com.example.itoll.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET

interface UserApi {


    @GET("users")
    suspend fun getUsers():Response<List<UserDto>>
}
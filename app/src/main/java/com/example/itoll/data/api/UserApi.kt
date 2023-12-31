package com.example.itoll.data.api

import com.example.itoll.data.dto.SearchUserDto
import com.example.itoll.data.dto.UserDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {


    @GET("users")
    suspend fun getUsers():Response<List<UserDto>>

    @GET("users/{userName}")
    suspend fun getUser(@Path("userName") userName:String):Response<UserDto>

    @GET("search/users")
    suspend fun searchUser(@Query("q") userName:String):Response<SearchUserDto>
}
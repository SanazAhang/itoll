package com.example.itoll.data.mapper

import com.example.itoll.data.dto.UserDto
import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import retrofit2.Response

inline fun <T> execute(request: () -> Response<T>): ResultData<T> =
    try {
        val response = request()
        if (response.isSuccessful) {
            ResultData.Success(response.body()!!)
        } else {
            ResultData.Failure(response.message())
        }
    } catch (ex: Exception) {
        ResultData.Error(ex)
    }


fun UserDto.mapToModel() = UserModel(
    login = login,
    id = id,
    node_id = node_id,
    avatar_url = avatar_url,
    gravatar_id = gravatar_id,
    url = url,
    html_url = html_url,
    followers_url = followers_url,
    following_url = following_url,
    gists_url = gists_url,
    starred_url = starred_url,
    subscriptions_url = subscriptions_url,
    organizations_url = organizations_url,
    repos_url = repos_url,
    events_url = events_url,
    received_events_url = received_events_url,
    type = type,
    site_admin = site_admin
)

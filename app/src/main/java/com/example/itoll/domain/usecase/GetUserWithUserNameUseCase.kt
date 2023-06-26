package com.example.itoll.domain.usecase

import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.repository.UserRepository
import javax.inject.Inject

class GetUserWithUserNameUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<String, ResultData<UserModel>> {

    override suspend fun execute(inpute: String): ResultData<UserModel> {
        return userRepository.getUser(inpute)
    }
}
package com.example.itoll.domain.usecase

import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersWithSearchUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<String, ResultData<List<UserModel>>> {
    override suspend fun execute(inpute: String): ResultData<List<UserModel>> {
        return userRepository.getUserwithSearch(inpute)
    }
}
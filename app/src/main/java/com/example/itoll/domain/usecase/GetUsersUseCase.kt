package com.example.itoll.domain.usecase


import com.example.itoll.domain.model.ResultData
import com.example.itoll.domain.model.UserModel
import com.example.itoll.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) :
    BaseUseCase<Unit, ResultData<List<UserModel>>> {
    override suspend fun execute(inpute: Unit): ResultData<List<UserModel>> {
        return userRepository.getUsers()
    }

}
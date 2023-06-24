package com.example.itoll.domain.usecase


import com.example.itoll.domain.repository.Repository
import javax.inject.Inject

class GetUseCase @Inject constructor(private val repository: Repository) :
    BaseUseCase<Unit, Unit> {
    override suspend fun execute(inpute: Unit): Unit {
      return Unit
    }
}
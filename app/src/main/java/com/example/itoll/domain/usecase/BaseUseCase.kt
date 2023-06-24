package com.example.itoll.domain.usecase

interface BaseUseCase<inpute, output> {
    suspend fun execute(inpute: inpute): output
}
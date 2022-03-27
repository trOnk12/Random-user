package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(resultSize: Int = 15, page: Int = 0): Result<List<User>> {

        return try {
            Result.success(userRepository.getUsers(resultSize))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}


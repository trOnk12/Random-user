package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userId: String): Result<User> {
        return try {
            Result.success(userRepository.get(userId))
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

}

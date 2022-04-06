package com.example.randomuser.domain.usecase

import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(userId: String): User {
        return userRepository.getUser(userId)
    }

}

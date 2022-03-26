package com.example.randomuser.ui.feature.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject constructor(
    private val getUsers: GetUsersUseCase
) : ViewModel() {

    var userState by mutableStateOf(UsersState())

    init {
        viewModelScope.launch {
            userState = userState.copy(isLoading = true)

            getUsers(15)
                .onFailure {
                    userState = userState.copy(
                        isLoading = false,
                        errorMessage = "we couldn't get the users"
                    )
                }
                .onSuccess { users ->
                    userState = userState.copy(
                        isLoading = false,
                        users = users
                    )
                }
        }
    }

}

data class UsersState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String = ""
)
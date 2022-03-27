package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomuser.domain.model.User
import com.example.randomuser.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel
@Inject constructor(
    private val getUsers: GetUsersUseCase
) : ViewModel() {

    var userState = MutableStateFlow(UsersState())

    init {
        viewModelScope.launch {
            userState.emit(UsersState(isLoading = true))

            getUsers(15)
                .onFailure {
                    userState.emit(
                        UsersState(
                            isLoading = false,
                            errorMessage = "we couldn't get the users"
                        )
                    )
                }
                .onSuccess { users ->
                    userState.emit(
                        UsersState(
                            isLoading = false,
                            users = users
                        )
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
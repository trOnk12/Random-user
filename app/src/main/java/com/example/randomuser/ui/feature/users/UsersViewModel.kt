package com.example.randomuser.ui.feature.users

import androidx.lifecycle.ViewModel
import com.example.randomuser.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(private val getUsers: GetUsersUseCase) : ViewModel() {


}
package com.example.randomuser.ui.feature.userdetail

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun UserDetailScreen(userDetailViewModel: UserDetailViewModel = hiltViewModel()) {

    val userDetailState by userDetailViewModel.userDetailState.collectAsState()

    Text(userDetailState.userDetail!!.name)
}



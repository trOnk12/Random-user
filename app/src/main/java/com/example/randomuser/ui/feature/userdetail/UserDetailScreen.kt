package com.example.randomuser.ui.feature.userdetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
fun UserDetailScreen(userDetailViewModel: UserDetailViewModel = hiltViewModel()) {
    val userDetailState by userDetailViewModel.userDetailState.collectAsState()

    if (userDetailState.isLoading == true) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth(
                    Alignment.CenterHorizontally
                )
                .wrapContentHeight(
                    Alignment.CenterVertically
                )
        )
    } else {
        Text(userDetailState.userDetail!!.name)
    }
}



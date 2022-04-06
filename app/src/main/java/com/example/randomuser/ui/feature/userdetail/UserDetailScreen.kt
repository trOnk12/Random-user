package com.example.randomuser.ui.feature.userdetail

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomuser.R


@Composable
fun UserDetailScreen(userDetailViewModel: UserDetailViewModel = hiltViewModel()) {
    val userDetailState by userDetailViewModel.userDetailState.collectAsState()

    if (userDetailState.isLoading) {
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
        userDetailState.userDetail?.apply {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.label_user_avatar),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(256.dp)
                        .fillMaxWidth()
                )
                Text(text = "Name: $name")
                Text(text = "Email: $email")
                Text(text = "Street: $street")
                Text(text = "City: $city")
                Text(text = "Postcode: $postcode")
                Text(text = "Gender: $gender")
                Text(text = "Phone: $phone")
            }
        }
    }
}



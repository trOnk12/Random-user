package com.example.randomuser.ui.feature.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomuser.data.source.local.entity.UserEntity
import com.example.randomuser.domain.model.User

@ExperimentalPagingApi
@Composable
fun Users(
    usersViewModel: UsersViewModel = hiltViewModel(),
    onOpenUserDetails: (String) -> Unit
) {
    val userListItems = usersViewModel.pagedUserList.collectAsLazyPagingItems()

    UsersScreen(userListItems, onOpenUserDetails)
}

@Composable
fun UsersScreen(
    usersState: LazyPagingItems<UserEntity>,
    onOpenUserDetails: (String) -> Unit
) {
    UsersList(
        usersPagingItems = usersState,
        onOpenUserDetails = onOpenUserDetails
    )
}

@Composable
fun UsersList(usersPagingItems: LazyPagingItems<UserEntity>, onOpenUserDetails: (String) -> Unit) {
    LazyColumn {
        items(usersPagingItems) { user ->
            if (user != null) {
                UserItem(
                    user = user,
                    onUserClicked = { onOpenUserDetails(user.id) }
                )
            }
        }
        loadState(usersPagingItems.loadState)
    }
}

fun LazyListScope.loadState(loadState: CombinedLoadStates) {
    loadState.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingItem() }
            }
            loadState.append is LoadState.Loading -> {
                item { LoadingItem() }
            }
            loadState.refresh is LoadState.Error -> {
            }
            loadState.append is LoadState.Error -> {
            }
        }
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                CenterHorizontally
            )
    )
}

@Composable
fun UserItem(
    user: UserEntity,
    onUserClicked: () -> Unit
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .clickable { onUserClicked() }
            .fillMaxWidth(),
    ) {
        with(user) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "test",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(128.dp)
                    .width(128.dp)
                    .clip(CircleShape)
            )

            Text(text = name)
        }
    }
}
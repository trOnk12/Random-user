package com.example.randomuser.ui.feature.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.randomuser.domain.model.User

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
    usersState: LazyPagingItems<User>,
    onOpenUserDetails: (String) -> Unit
) {
    UsersList(
        usersPagingItems = usersState,
        onOpenUserDetails = onOpenUserDetails
    )
}

@Composable
fun UsersList(usersPagingItems: LazyPagingItems<User>, onOpenUserDetails: (String) -> Unit) {
    LazyColumn {
        items(usersPagingItems) { user ->
            if (user != null) {
                UserItem(
                    user = user,
                    onUserClicked = { onOpenUserDetails(user.uuid) }
                )
            }
        }
        usersPagingItems.apply {
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
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    )
}

@Composable
fun UserItem(user: User, onUserClicked: () -> Unit) {
    Column(
        Modifier
            .clickable { onUserClicked() }
            .fillMaxWidth()) {
        with(user) {
            Text(text = name)
        }
    }
}



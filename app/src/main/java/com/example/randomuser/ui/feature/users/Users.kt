package com.example.randomuser.ui.feature.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.randomuser.domain.model.User

@Composable
fun Users(
    usersViewModel: UsersViewModel = hiltViewModel(),
    onOpenUserDetails: (String) -> Unit
) {
    val usersState: UsersState by usersViewModel.userState.collectAsState()

    UsersScreen(usersState, onOpenUserDetails)
}

@Composable
fun UsersScreen(
    usersState: UsersState,
    onOpenUserDetails: (String) -> Unit
) {
    UsersList(
        users = usersState.users,
        onOpenUserDetails = onOpenUserDetails
    )
}

@Composable
fun UsersList(users: List<User>, onOpenUserDetails: (String) -> Unit) {
    LazyColumn {
        items(users) { user ->
            UserItem(
                user = user,
                onUserClicked = { onOpenUserDetails(user.id) }
            )
        }
    }
}

@Composable
fun UserItem(user: User, onUserClicked: () -> Unit) {
    Column(Modifier.clickable { onUserClicked() }) {
        with(user) {
            Text(text = id)
        }
    }
}



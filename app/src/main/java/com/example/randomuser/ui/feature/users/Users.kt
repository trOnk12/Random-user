package com.example.randomuser.ui.feature.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.CombinedLoadStates
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomuser.R
import com.example.randomuser.ui.feature.users.model.UserItem
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
@Composable
fun Users(
    usersViewModel: UsersViewModel = hiltViewModel(),
    onOpenUserDetails: (String) -> Unit
) {
    val userListItems = rememberFlowWithLifecycle(usersViewModel.pagedUserList)
        .collectAsLazyPagingItems()

    UsersScreen(userListItems, onOpenUserDetails)
}

@Composable
fun UsersScreen(
    usersState: LazyPagingItems<UserItem>,
    onOpenUserDetails: (String) -> Unit
) {
    UsersList(
        usersPagingItems = usersState,
        onOpenUserDetails = onOpenUserDetails
    )
}

@Composable
fun UsersList(
    usersPagingItems: LazyPagingItems<UserItem>,
    onOpenUserDetails: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        items(usersPagingItems) { userItem ->
            if (userItem != null) {
                UserListItem(
                    userItem = userItem,
                    onUserClicked = { onOpenUserDetails(userItem.id) }
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
fun UserListItem(
    userItem: UserItem,
    onUserClicked: () -> Unit
) {
    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .clickable { onUserClicked() }
            .fillMaxWidth(),
    ) {
        with(userItem) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.label_user_avatar),
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

@Composable
fun <T> rememberFlowWithLifecycle(
    flow: Flow<T>,
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED
): Flow<T> = remember(flow, lifecycle) {
    flow.flowWithLifecycle(
        lifecycle = lifecycle,
        minActiveState = minActiveState
    )
}

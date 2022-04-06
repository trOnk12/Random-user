package com.example.randomuser.ui.feature

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.paging.ExperimentalPagingApi
import com.example.randomuser.ui.feature.userdetail.UserDetailScreen
import com.example.randomuser.ui.feature.users.Users
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@ExperimentalPagingApi
@ExperimentalAnimationApi
@Composable
fun Home() {
    val navController = rememberAnimatedNavController()

    Scaffold {
        AnimatedNavHost(
            navController = navController,
            startDestination = HomeNavigation.User.route
        ) {
            composable(route = HomeNavigation.User.route) {
                Users(onOpenUserDetails = { userId ->
                    navController.navigate(
                        HomeNavigation.UserDetail.createRoute(userId)
                    )
                })
            }
            composable(
                route = HomeNavigation.UserDetail.route,
                arguments = listOf(
                    navArgument(name = "userId") { type = NavType.StringType },
                )
            ) {
                UserDetailScreen()
            }
        }
    }

}

sealed class HomeNavigation(val route: String) {
    object User : HomeNavigation(route = "/user")

    object UserDetail : HomeNavigation(route = "/user/{$USER_DETAIL_ARGUMENT_ID}/details") {

        fun createRoute(userId: String): String {
            return "/user/$userId/details"
        }

    }
}

const val USER_DETAIL_ARGUMENT_ID = "userId"
package com.example.randomuser.ui.feature

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.example.randomuser.ui.feature.users.Users
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Home() {
    val navController = rememberAnimatedNavController()

    Scaffold {
        AnimatedNavHost(
            navController = navController,
            startDestination = "users"
        ) {
            composable("users") {
                Users()
            }
        }

    }

}
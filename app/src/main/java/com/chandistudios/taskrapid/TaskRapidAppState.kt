package com.chandistudios.taskrapid

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class TaskRapidAppState(
    val navController: NavHostController
) {
    fun navigateBack() {
        navController.popBackStack()
    }
}

@Composable
fun rememberTaskRapidAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController) {
    TaskRapidAppState(navController)
}
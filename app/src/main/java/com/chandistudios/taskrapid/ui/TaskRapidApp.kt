package com.chandistudios.taskrapid.ui

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chandistudios.taskrapid.TaskRapidAppState
import com.chandistudios.taskrapid.rememberTaskRapidAppState
import com.chandistudios.taskrapid.ui.home.Home
import com.chandistudios.taskrapid.ui.login.Login
import com.chandistudios.taskrapid.ui.profile.EditProfile
import com.chandistudios.taskrapid.ui.profile.ViewProfile
import com.chandistudios.taskrapid.ui.signup.SignUp
import com.chandistudios.taskrapid.ui.task.add.AddTask
import com.chandistudios.taskrapid.ui.task.add.AddTaskViewModel
import com.chandistudios.taskrapid.ui.task.edit.EditTask
import com.chandistudios.taskrapid.ui.task.view.ViewTask


@Composable
fun TaskRapidApp(
    sharedPrefsCredentials: SharedPreferences,
    appState: TaskRapidAppState = rememberTaskRapidAppState()
) {
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            Login(
                navController = appState.navController,
                sharedPrefsCredentials = sharedPrefsCredentials
            )
        }
        composable(route = "signup") {
            SignUp(
                onBackPress = appState::navigateBack,
                navController = appState.navController,
                sharedPrefs = sharedPrefsCredentials
            )
        }
        composable(route = "home") {
            Home(navController = appState.navController)
        }
        composable(route = "addtask") {
            AddTask(onBackPress = appState::navigateBack)
        }
        composable(route = "edittask") {
            // TODO: HW2
            EditTask(onBackPress = appState::navigateBack)
        }
        composable(route = "viewtask") {
            // TODO: HW2
            ViewTask(onBackPress = appState::navigateBack)
        }
        composable(route = "editprofile") {
            EditProfile(
                onBackPress = appState::navigateBack,
                navController = appState.navController,
                sharedPrefs = sharedPrefsCredentials
            )
        }
        composable(route = "viewprofile") {
            ViewProfile(
                onBackPress = appState::navigateBack,
                navController = appState.navController,
                sharedPrefs = sharedPrefsCredentials
            )
        }

    }
}
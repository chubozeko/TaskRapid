package com.chandistudios.taskrapid.ui

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
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
import com.chandistudios.taskrapid.ui.task.edit.EditTask


@RequiresApi(Build.VERSION_CODES.O)
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
            Home(
                navController = appState.navController
            )
        }
        composable(route = "addtask") {
            AddTask(onBackPress = appState::navigateBack)
        }
        composable(route = "edittask") {
            EditTask(onBackPress = appState::navigateBack)
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
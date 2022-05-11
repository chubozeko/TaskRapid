package com.chandistudios.taskrapid

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.impl.model.Preference
import com.chandistudios.taskrapid.ui.TaskRapidApp
import com.chandistudios.taskrapid.ui.login.Login
import com.chandistudios.taskrapid.ui.theme.TaskRapidTheme
import com.chandistudios.taskrapid.util.UserProfile

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPrefsCredentials: SharedPreferences = getSharedPreferences("LOGIN_CREDENTIALS", Context.MODE_PRIVATE)
        InitLoginCredentials(sharedPrefsCredentials)

        setContent {
            TaskRapidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TaskRapidApp(sharedPrefsCredentials)
                }
            }
        }
    }
}

fun InitLoginCredentials(sharedPrefs: SharedPreferences) {
    val profile = UserProfile()
    var editor = sharedPrefs.edit()
    // User 1:
    editor.putString(profile.generateUsernameKey("admin"),"admin")
    editor.putString(profile.generatePasswordKey("admin"),"Admin12345")
    // User 2:
    editor.putString(profile.generateUsernameKey("chubo"),"chubo")
    editor.putString(profile.generatePasswordKey("chubo"),"Zeko21")
    // User 3:
    editor.putString(profile.generateUsernameKey("temp"),"temp")
    editor.putString(profile.generatePasswordKey("temp"),"Oulu2022")
    editor.commit()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TaskRapidTheme {

    }
}
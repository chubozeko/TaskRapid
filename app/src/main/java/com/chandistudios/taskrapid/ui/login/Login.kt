package com.chandistudios.taskrapid.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.impl.model.PreferenceDao
import com.chandistudios.taskrapid.MainActivity
import com.chandistudios.taskrapid.R
import com.chandistudios.taskrapid.ui.theme.TaskRapidTheme
import com.chandistudios.taskrapid.util.UserProfile
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Login(
    navController: NavController,
    sharedPrefsCredentials: SharedPreferences
) {
    val context: Context = LocalContext.current
    Surface(modifier = Modifier.fillMaxSize()) {
        val username = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .scrollable(state = rememberScrollState(0), orientation = Orientation.Vertical)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(R.drawable.taskrapid_logo),
                contentDescription = "Circle Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
            )
            Text(
                text = "Login",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
//                onClick = { navController.navigate("home") },
                onClick = { CheckCredentials(context, navController, sharedPrefsCredentials, username.value, password.value) },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
            Button(
                onClick = { navController.navigate("signup") },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Sign up")
            }
        }
    }
}

fun CheckCredentials(context: Context, navController: NavController, sharedPrefsCredentials: SharedPreferences, user: String, pw: String) {
    val profile = UserProfile()
    if (sharedPrefsCredentials.getString(profile.generateUsernameKey(user), null) != null) {
        if (pw == sharedPrefsCredentials.getString(profile.generatePasswordKey(user), null)) {
            // save current user to SharedPreferences
            var editor = sharedPrefsCredentials.edit()
            editor.putString("currentUser", user)
            editor.commit()
            navController.navigate("home")
        } else {
            println("Invalid password!")
            Toast.makeText(context,"Invalid password!",Toast.LENGTH_SHORT).show()
        }
    } else {
        println("Invalid username!")
        Toast.makeText(context,"Invalid username!",Toast.LENGTH_SHORT).show()
//        Toast.makeText(null, "Invalid username!", Toast.LENGTH_SHORT)
    }
}

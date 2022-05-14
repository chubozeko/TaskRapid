package com.chandistudios.taskrapid.ui.signup

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.impl.model.PreferenceDao
import com.chandistudios.taskrapid.R
import com.chandistudios.taskrapid.util.UserProfile
import com.google.accompanist.insets.systemBarsPadding
import java.util.*

@Composable
fun SignUp(
    onBackPress: () -> Unit,
    navController: NavController,
    sharedPrefs: SharedPreferences
) {
    val context: Context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val username = rememberSaveable { mutableStateOf("") }
        val name = rememberSaveable { mutableStateOf("") }
        val surname = rememberSaveable { mutableStateOf("") }
        val email = rememberSaveable { mutableStateOf("") }
        val phone_number = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        val confirm_password = rememberSaveable { mutableStateOf("") }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.taskrapid_logo),
                contentDescription = "Circle Image",
                modifier = Modifier
                    .size(120.dp)
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp),
            )
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { data -> name.value = data },
                    label = { Text("Name") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    value = surname.value,
                    onValueChange = { data -> surname.value = data },
                    label = { Text("Surname") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = { data -> username.value = data },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { data -> email.value = data },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = phone_number.value,
                onValueChange = { data -> phone_number.value = data },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { data -> password.value = data },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = confirm_password.value,
                onValueChange = { data -> confirm_password.value = data },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val profile = UserProfile()
                    // 1. check if username already exists
                    if (sharedPrefs.getString(profile.generateUsernameKey(username.value), null) != null) {
                        // 2. check if password == confirm_password
                        if (password.value == confirm_password.value && password.value != null) {
                            // 3. save profile to SharedPreferences
                            var editor = sharedPrefs.edit()
                            editor.putString(profile.generateUsernameKey(username.value),username.value)
                            editor.putString(profile.generateNameKey(username.value),name.value)
                            editor.putString(profile.generateSurnameKey(username.value),surname.value)
                            editor.putString(profile.generateEmailKey(username.value),email.value)
                            editor.putString(profile.generatePhoneNumberKey(username.value),phone_number.value)
                            editor.putString(profile.generatePasswordKey(username.value),password.value)
                            editor.commit()
                            println("Account CREATED successfully! You may now Login.")
                            Toast.makeText(context, "Account CREATED successfully! You may now Login.", Toast.LENGTH_SHORT).show()
                        } else {
                            println("INVALID Password! Please make sure you type the same password twice.")
                            Toast.makeText(context, "INVALID Passwords! Please make sure you type the same password twice.", Toast.LENGTH_LONG).show()
                        }
                    } else if (username.value == null || email.value == null || (password.value == null && confirm_password.value == null)) {
                        println("Username, Email and Password are required to create an Account!")
                        Toast.makeText(context, "Username, Email and Password are required to create an Account!", Toast.LENGTH_LONG).show()
                    } else {
                        println("USERNAME already in use: Please choose a different Username.")
                        Toast.makeText(context, "USERNAME already in use! Please choose a different Username.", Toast.LENGTH_LONG).show()
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Create Account")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Already have an account?",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            )
            Button(
                onClick = onBackPress,
                enabled = true,
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
            ) {
                Text(text = "Login")
            }
        }
    }
}

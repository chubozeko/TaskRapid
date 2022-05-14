package com.chandistudios.taskrapid.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.work.impl.model.PreferenceDao
import com.chandistudios.taskrapid.util.UserProfile
import com.google.accompanist.insets.systemBarsPadding
import java.util.*

@Composable
fun ViewProfile(
    onBackPress: () -> Unit,
    navController: NavController,
    sharedPrefs: SharedPreferences
) {
    val context: Context = LocalContext.current
    val profile = UserProfile()
    val currentUser = sharedPrefs.getString("currentUser", "temp").toString()

    val username = rememberSaveable { mutableStateOf("") }
    username.value = sharedPrefs.getString(profile.generateUsernameKey(currentUser), "").toString()
    val email = rememberSaveable { mutableStateOf("") }
    email.value = sharedPrefs.getString(profile.generateEmailKey(currentUser), "").toString()
    val name = rememberSaveable { mutableStateOf("") }
    name.value = sharedPrefs.getString(profile.generateNameKey(currentUser), "").toString()
    val surname = rememberSaveable { mutableStateOf("") }
    surname.value = sharedPrefs.getString(profile.generateSurnameKey(currentUser), "").toString()
    val phone_number = rememberSaveable { mutableStateOf("") }
    phone_number.value = sharedPrefs.getString(profile.generatePhoneNumberKey(currentUser), "").toString()

    Column(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxWidth()
    ) {
        TopAppBar {
            IconButton(
                onClick = onBackPress
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "My Profile")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ConstraintLayout(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                val (imgProPic, txtNameSurname, txtUsername, txtEmail,
                     txtPhoneNr, btnEdit, btnSignOut, divider) = createRefs()
                // Profile Picture
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .size(120.dp)
                        .constrainAs(imgProPic) {
//                            linkTo(
//                                start = parent.start,
//                                end = parent.end,
//                                startMargin = 8.dp,
//                                endMargin = 8.dp,
//                                bias = 0f
//                            )
                            top.linkTo(parent.top, margin = 4.dp)
                            bottom.linkTo(txtNameSurname.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Name & Surname
                Text(
                    text = name.value + " " + surname.value,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .constrainAs(txtNameSurname) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(imgProPic.bottom, margin = 4.dp)
                            bottom.linkTo(txtUsername.top, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Username
                Text(
                    text = "Username: " + username.value,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .constrainAs(txtUsername) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(imgProPic.bottom, margin = 4.dp)
                            bottom.linkTo(txtEmail.top, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Email
                Text(
                    text = "Email: " + email.value,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .constrainAs(txtEmail) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(txtUsername.bottom, margin = 4.dp)
                            bottom.linkTo(txtPhoneNr.top, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Phone Number
                Text(
                    text = "Phone Number: " + phone_number.value,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.primaryVariant,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 2.dp)
                        .constrainAs(txtPhoneNr) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(txtEmail.bottom, margin = 4.dp)
                            bottom.linkTo(divider.top, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(divider) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(txtPhoneNr.bottom, margin = 2.dp)
                            bottom.linkTo(btnEdit.top, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Edit Changes
                Button(
                    onClick = { navController.navigate(route = "editprofile") },
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnEdit) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(divider.bottom, margin = 2.dp)
                            bottom.linkTo(btnSignOut.bottom, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Profile"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Edit Profile")
                }

                // Sign Out
                Button(
                    onClick = {
                        // TODO: Add "Are you sure?" pop-up dialog
                        // set currentUser to "null"
                        var editor = sharedPrefs.edit()
                        editor.putString("currentUser","")
                        editor.commit()
                        println("You have successfully SIGNED OUT!")
                        Toast.makeText(context, "You have successfully SIGNED OUT!", Toast.LENGTH_SHORT).show()
                        // Return to Login screen
                        navController.popBackStack("login", inclusive = false)
//                        navController.navigate(route = "login")
                    },
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnSignOut) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(btnEdit.bottom, margin = 4.dp)
                            bottom.linkTo(parent.bottom, 4.dp)
                            width = Dimension.preferredWrapContent
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = "Sign Out"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Sign Out")
                }

            }
        }
    }
}
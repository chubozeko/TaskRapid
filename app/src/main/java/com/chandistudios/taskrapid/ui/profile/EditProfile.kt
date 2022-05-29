package com.chandistudios.taskrapid.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.chandistudios.taskrapid.util.UserProfile
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun EditProfile(
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
    val password_old = rememberSaveable { mutableStateOf("") }
    val password_new = rememberSaveable { mutableStateOf("") }

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
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Edit Profile")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ConstraintLayout() {
                val (txtUsername, txtEmail,
                    tfName, tfSurname, tfPhoneNr,
                    tfOldPw, tfNewPw, imgProPic,
                    btnSave, divider) = createRefs()
                // Profile Picture
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Edit Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .constrainAs(imgProPic) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(parent.top, margin = 4.dp)
                            bottom.linkTo(txtUsername.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                        .clickable { }
                )
                // Username
                Text(
                    text = "Username: " + username.value,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .constrainAs(txtUsername) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(imgProPic.bottom, margin = 4.dp)
                            bottom.linkTo(txtEmail.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Email
                Text(
                    text = "Email: " + email.value,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .constrainAs(txtEmail) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 16.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(txtUsername.bottom, margin = 4.dp)
                            bottom.linkTo(tfName.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Name
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { data -> name.value = data },
                    label = { Text("Name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfName) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(imgProPic.bottom, margin = 4.dp)
                            bottom.linkTo(tfSurname.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Surname
                OutlinedTextField(
                    value = surname.value,
                    onValueChange = { data -> surname.value = data },
                    label = { Text("Surname") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfSurname) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(tfName.bottom, margin = 4.dp)
                            bottom.linkTo(tfPhoneNr.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Phone Number
                OutlinedTextField(
                    value = phone_number.value,
                    onValueChange = { data -> phone_number.value = data },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfPhoneNr) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(tfSurname.bottom, margin = 4.dp)
                            bottom.linkTo(tfOldPw.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Enter Old Password
                OutlinedTextField(
                    value = password_old.value,
                    onValueChange = { data -> password_old.value = data },
                    label = { Text("Enter Old Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfOldPw) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(tfPhoneNr.bottom, margin = 4.dp)
                            bottom.linkTo(tfNewPw.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Enter New Password
                OutlinedTextField(
                    value = password_new.value,
                    onValueChange = { data -> password_new.value = data },
                    label = { Text("Enter New Password") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tfNewPw) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(tfOldPw.bottom, margin = 4.dp)
                            bottom.linkTo(divider.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                Spacer(modifier = Modifier.height(24.dp))
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
                            top.linkTo(tfNewPw.bottom, margin = 4.dp)
                            bottom.linkTo(btnSave.top, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                )
                // Save Changes
                Button(
                    onClick = {
                        val profile = UserProfile()
                        val currentUser = sharedPrefs.getString("currentUser", "temp").toString()
                        // save profile changes
                        var editor = sharedPrefs.edit()
                        editor.putString(
                            profile.generateNameKey(currentUser),
                            name.value
                        )
                        editor.putString(
                            profile.generateSurnameKey(currentUser),
                            surname.value
                        )
                        editor.putString(
                            profile.generatePhoneNumberKey(currentUser),
                            phone_number.value
                        )
                        // check if the old_password was entered and is equal to the saved old_password
                        if (password_old.value == sharedPrefs.getString(profile.generatePasswordKey(currentUser),null)) {
                            // check if new_password != old_password if they are entered/changed
                            if (password_old.value != password_new.value && (password_new.value != null)) {
                                // save new password
                                editor.putString(
                                    profile.generatePasswordKey(currentUser.toString()),
                                    password_new.value
                                )
                            } else {
                                // please enter a different new password
                                println("Password ERROR: Please enter a different New Password")
                                Toast.makeText(context, "Password ERROR: Please enter a different New Password", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            if (password_old.value != null) {
                                // please enter the correct old password
                                println("INVALID Password! Please enter the correct current Password")
                                Toast.makeText(context, "INVALID Password! Please enter the correct current Password", Toast.LENGTH_LONG).show()
                            }
                        }
                        editor.commit()
                        println("Profile changes SAVED!")
                        Toast.makeText(context, "Profile changes SAVED!", Toast.LENGTH_SHORT).show()
                    },
                    enabled = true,
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(btnSave) {
                            linkTo(
                                start = parent.start,
                                end = parent.end,
                                startMargin = 8.dp,
                                endMargin = 8.dp,
                                bias = 0f
                            )
                            top.linkTo(tfNewPw.bottom, margin = 4.dp)
                            bottom.linkTo(parent.bottom, 8.dp)
                            width = Dimension.preferredWrapContent
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Save changes to Profile"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save Changes")
                }

            }
        }
    }
}
package com.chandistudios.taskrapid.util

class UserProfile {
    // The user's profile details would be stored in SharedPreferences using a combination of the
    //      USERNAME_PREFIX & username, with the relevant profile property suffix
    //          => key = USERNAME_PREFIX + <username> + <PROPERTY_SUFFIX>
    //          e.g. to retrieve the surname of the user 'admin': key = user_admin_sname
    // USER PROFILE PROPERTIES: name, surname, email, phone_number, username, password
    val USER_PREFIX: String = "user_"               // e.g. key: user_admin
    val USERNAME_SUFFIX: String = "_usern"          // e.g. key: user_admin_usern
    val PASSWORD_SUFFIX: String = "_pw"             // e.g. key: user_admin_pw
    val USER_NAME_SUFFIX: String = "_name"          // e.g. key: user_admin_name
    val USER_SURNAME_SUFFIX: String = "_sname"      // e.g. key: user_admin_sname
    val USER_EMAIL_SUFFIX: String = "_email"        // e.g. key: user_admin_email
    val USER_PHONE_NR_SUFFIX: String = "_phonenr"   // e.g. key: user_admin_phonenr

    fun generateUsernameKey(username: String): String {
        return USER_PREFIX + username + USERNAME_SUFFIX
    }

    fun generatePasswordKey(username: String): String {
        return USER_PREFIX + username + PASSWORD_SUFFIX
    }

    fun generateNameKey(username: String): String {
        return USER_PREFIX + username + USER_NAME_SUFFIX
    }

    fun generateSurnameKey(username: String): String {
        return USER_PREFIX + username + USER_SURNAME_SUFFIX
    }

    fun generateEmailKey(username: String): String {
        return USER_PREFIX + username + USER_EMAIL_SUFFIX
    }

    fun generatePhoneNumberKey(username: String): String {
        return USER_PREFIX + username + USER_PHONE_NR_SUFFIX
    }
}
package com.btust.tazoionaluka

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class GetUser(
    val fName: String = "",
    val lName: String = "",
    val email: String = ""
)
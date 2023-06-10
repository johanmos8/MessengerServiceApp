package com.example.data.entities

data class UserFB(
    val name: String,
    val phoneNumber: String,
    val profilePicture: String?,
    val status: Boolean =true,
    val owner: Boolean=false
)



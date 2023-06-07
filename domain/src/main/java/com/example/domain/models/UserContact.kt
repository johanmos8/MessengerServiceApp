package com.example.domain.models

data class UserContact(
    val userId: String,
    val name: String,
    val phoneNumber:String,
    val profilePicture: String?,
    val status: Boolean? =true,
)



package com.example.domain.models

data class UserContact(
    val name: String,
    val phoneNumber:String,
    val profilePicture: String?,
    val status: Boolean? =true,
    val owner: Boolean?=false
)



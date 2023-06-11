package com.example.domain.models

data class UserContact(
    val name: String,
    val phoneNumber:String,
    val profilePicture: String?,
    val status: Boolean? =true,
    val owner: Boolean?=false
){
    fun toMap(): Map<String, Any?> {
        val map = HashMap<String, Any?>()
        map["name"] = name
        map["phoneNumber"] = phoneNumber
        map["profilePicture"] = profilePicture
        map["status"] = status
        map["owner"] = owner
        return map
    }
}



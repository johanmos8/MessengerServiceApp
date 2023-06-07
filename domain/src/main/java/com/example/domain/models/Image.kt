package com.example.domain.models


data class Image(
    val imageId: String,
    val userId: String,
    val imageUrl: String,
    val timestamp: Long
)

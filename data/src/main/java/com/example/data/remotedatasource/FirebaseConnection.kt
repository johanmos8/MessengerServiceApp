package com.example.data.remotedatasource

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseConnection {
    private val databaseUrl = "https://chatchallenge-cbf1d-default-rtdb.firebaseio.com/"
    val database: DatabaseReference by lazy {
        FirebaseDatabase.getInstance(databaseUrl).reference
    }
}


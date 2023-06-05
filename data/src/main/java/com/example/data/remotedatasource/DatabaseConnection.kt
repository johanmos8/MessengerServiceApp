package com.example.data.remotedatasource

import android.util.Log
import com.example.data.entities.UserContact
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class DatabaseConnection {
    val databaseUrl = "https://chatchallenge-cbf1d-default-rtdb.firebaseio.com/"

    // Write a message to the database
    val database = Firebase.database.reference

    /*private val myRef = database.getReference("message")
    fun saveMessage(message: String) {
        myRef.setValue(message)
    }

    fun readMessage() {

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d("Prueba", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("Prueba", "Failed to read value.", error.toException())
            }
        })
    }*/

    fun writeNewUser(phone: String, name: String, profilePicture: String) {
        val user = UserContact(name = name, profilePicture=profilePicture)
        database.child("users").child(phone).setValue(user)
    }
}
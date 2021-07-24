package com.example.simple_app.model

class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val photo: Int
) {

    val fullName
        get() = "$firstName $lastName"

}
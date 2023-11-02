package com.example.handybook.model

import java.io.Serializable

data class User(
    val username: String,
    val fullname: String,
    val email:String,
    val password:String
):Serializable
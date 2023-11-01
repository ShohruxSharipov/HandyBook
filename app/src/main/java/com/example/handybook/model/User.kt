package com.example.handybook.model

import java.io.Serializable

data class User(
    val user_name: String,
    val full_name: String,
    val email:String,
    val password:String
):Serializable
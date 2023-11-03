package com.example.handybook.model

import java.io.Serializable

data class AddUser(
    val access_token: String,
    val id: Int,
    val fullname:String,
    val username: String
):Serializable
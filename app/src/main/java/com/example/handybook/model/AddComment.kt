package com.example.handybook.model

class AddComment(
    val book_id: Int,
    var id:Int = 0,
    val reyting:Int = 2,
    val text: String,
    val user_id: Int
)
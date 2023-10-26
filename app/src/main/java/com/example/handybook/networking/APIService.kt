package com.example.handybook.networking

import com.example.handybook.model.Book
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("/book-api")
    fun getAllBooks():Call<List<Book>>
}
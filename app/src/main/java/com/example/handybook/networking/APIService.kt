package com.example.handybook.networking

import com.example.handybook.model.Book
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/book-api")
    fun getAllBooks():Call<List<Book>>

    @GET("/book-api/view")
    fun getthebook(@Query("id") id:Int):Call<Book>
}
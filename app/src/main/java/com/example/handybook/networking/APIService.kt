package com.example.handybook.networking

import com.example.handybook.model.Book
import com.example.handybook.model.Category
import com.example.handybook.model.Comment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("/book-api")
    fun getAllBooks():Call<List<Book>>

    @GET("/book-api/view")
    fun getthebook(@Query("id") id:Int):Call<Book>

    @GET("/book-api/all-category")
    fun getCategories():Call<List<Category>>

    @GET("/book-api/comment")
    fun bookComment(@Query("id") id: Int):Call<List<Comment>>
}
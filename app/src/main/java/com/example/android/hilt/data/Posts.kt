package com.example.android.hilt.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val id: Int,
    val title: String,
)

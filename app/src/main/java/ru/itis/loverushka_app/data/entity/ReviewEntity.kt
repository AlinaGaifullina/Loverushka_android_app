package ru.itis.loverushka_app.data.entity

import androidx.room.PrimaryKey

data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val reviewId: Int,
    val orderId: Int,
    val phoneNumber: String,
    val rating: Int,
    val date: String,
    val comment: String
)

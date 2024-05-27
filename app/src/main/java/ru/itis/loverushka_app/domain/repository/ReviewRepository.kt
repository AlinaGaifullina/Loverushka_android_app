package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.Address
import ru.itis.loverushka_app.domain.model.Review

interface ReviewRepository {

    suspend fun addReview(review: Review)
}
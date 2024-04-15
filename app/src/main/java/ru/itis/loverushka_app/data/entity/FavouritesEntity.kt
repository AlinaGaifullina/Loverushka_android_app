package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites")
data class FavouritesEntity(

    @PrimaryKey(autoGenerate = true)
    val favouritesId: Int = 0,
    val phoneNumber: String,
    val dishId: Int
)
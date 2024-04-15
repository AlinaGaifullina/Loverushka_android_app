package ru.itis.loverushka_app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@Entity(tableName = "carts")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int,
    val userPhoneNumber: String,
    val address: String,
    val dishes: List<Int>,
    val price: Int,
    val checkedDishes: List<Int>,
    val numberOfDishes: List<Int>,
)


object Converters {
    @TypeConverter
    fun listToString(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(json: String?): List<Int> {
        return Gson().fromJson(json, object : TypeToken<List<Int?>?>() {}.type)
    }
}
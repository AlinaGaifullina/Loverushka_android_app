package ru.itis.loverushka_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.loverushka_app.data.dao.CartDao
import ru.itis.loverushka_app.data.entity.CartEntity
import ru.itis.loverushka_app.data.entity.Converters

private const val DATABASE_NAME = "cart.db"

@TypeConverters(Converters::class)
@Database(
    entities = [CartEntity::class],
    version = 1
)

abstract class CartDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): CartDatabase = Room
            .databaseBuilder(context.applicationContext, CartDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun cartDao() : CartDao

}
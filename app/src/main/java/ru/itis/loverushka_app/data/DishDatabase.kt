package ru.itis.loverushka_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.loverushka_app.data.dao.DishDao
import ru.itis.loverushka_app.data.entity.DishEntity

private const val DATABASE_NAME = "dish.db"

@Database(
    entities = [DishEntity::class],
    version = 1
)

abstract class DishDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): DishDatabase = Room
            .databaseBuilder(context.applicationContext, DishDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun dishDao() : DishDao

}
package ru.itis.loverushka_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.loverushka_app.data.dao.FavouritesDao
import ru.itis.loverushka_app.data.entity.FavouritesEntity

private const val DATABASE_NAME = "favourites.db"

@Database(
    entities = [FavouritesEntity::class],
    version = 1
)

abstract class FavouritesDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): FavouritesDatabase = Room
            .databaseBuilder(context.applicationContext, FavouritesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun favouritesDao() : FavouritesDao

}
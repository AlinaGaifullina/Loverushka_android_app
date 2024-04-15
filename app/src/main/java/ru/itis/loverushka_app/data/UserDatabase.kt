package ru.itis.loverushka_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.loverushka_app.data.dao.UserDao
import ru.itis.loverushka_app.data.entity.Converters
import ru.itis.loverushka_app.data.entity.UserEntity

private const val DATABASE_NAME = "app.db"

@TypeConverters(Converters::class)
@Database(
    entities = [UserEntity::class],
    version = 1
)

abstract class UserDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): UserDatabase = Room
            .databaseBuilder(context.applicationContext, UserDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun userDao() : UserDao

}
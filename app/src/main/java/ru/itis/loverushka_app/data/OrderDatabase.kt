package ru.itis.loverushka_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.itis.loverushka_app.data.dao.OrderDao
import ru.itis.loverushka_app.data.entity.Converters
import ru.itis.loverushka_app.data.entity.OrderEntity

private const val DATABASE_NAME = "order.db"

@TypeConverters(Converters::class)
@Database(
    entities = [OrderEntity::class],
    version = 1
)

abstract class OrderDatabase : RoomDatabase(){

    companion object {

        fun get(context: Context): OrderDatabase = Room
            .databaseBuilder(context.applicationContext, OrderDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    abstract fun orderDao() : OrderDao

}
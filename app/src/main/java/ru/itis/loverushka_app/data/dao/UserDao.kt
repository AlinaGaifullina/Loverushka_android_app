package ru.itis.loverushka_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.loverushka_app.data.entity.UserEntity

@Dao
interface UserDao{

    @Query("select * from users where phoneNumber = :phoneNumber")
    suspend fun getUserByNumber(phoneNumber: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

}
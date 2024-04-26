package ru.itis.loverushka_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.loverushka_app.data.entity.FavouritesEntity

@Dao
interface FavouritesDao {

    @Query("select * from favourites where phoneNumber = :phoneNumber")
    suspend fun getFavouritesByPhoneNumber(phoneNumber: String): List<FavouritesEntity>

    @Query("select * from favourites where favouritesId = :favouritesId")
    suspend fun getFavouritesById(favouritesId: Int): FavouritesEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavourites(favourites: FavouritesEntity)

    @Query("DELETE FROM favourites WHERE dishId = :id and phoneNumber = :phoneNumber")
    suspend fun deleteFavourites(phoneNumber: String, id: Int)
}
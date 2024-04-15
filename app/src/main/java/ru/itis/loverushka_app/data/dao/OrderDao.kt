package ru.itis.loverushka_app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.itis.loverushka_app.data.entity.OrderEntity

@Dao
interface OrderDao {

    @Query("select * from orders where orderId = :orderId")
    suspend fun getOrderById(orderId: Int): OrderEntity

    @Query("select * from orders where userPhoneNumber = :phoneNumber")
    suspend fun getOrdersByPhoneNumber(phoneNumber: String): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Delete
    suspend fun deleteOrder(order: OrderEntity)
}
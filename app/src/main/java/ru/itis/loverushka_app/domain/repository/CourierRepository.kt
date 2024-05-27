package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.Chef
import ru.itis.loverushka_app.domain.model.Courier

interface CourierRepository {

    suspend fun addCourier(courier: Courier)
    suspend fun deleteCourier(courierId: Int)

}
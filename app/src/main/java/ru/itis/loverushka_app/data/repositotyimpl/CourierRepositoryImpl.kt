package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.domain.model.Courier
import ru.itis.loverushka_app.domain.repository.CourierRepository

class CourierRepositoryImpl : CourierRepository {
    override suspend fun addCourier(courier: Courier) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCourier(courierId: Int) {
        TODO("Not yet implemented")
    }
}
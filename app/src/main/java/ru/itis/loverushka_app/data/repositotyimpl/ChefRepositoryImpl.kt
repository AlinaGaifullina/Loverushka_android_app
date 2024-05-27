package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.domain.model.Chef
import ru.itis.loverushka_app.domain.repository.ChefRepository

class ChefRepositoryImpl : ChefRepository {
    override suspend fun addChef(chef: Chef) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteChef(chefId: Int) {
        TODO("Not yet implemented")
    }
}
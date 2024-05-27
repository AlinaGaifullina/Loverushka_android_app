package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.Address
import ru.itis.loverushka_app.domain.model.Chef

interface ChefRepository {

    //TODO
    suspend fun addChef(chef: Chef)
    suspend fun deleteChef(chefId: Int)
}
package ru.itis.loverushka_app.data.repositotyimpl

import ru.itis.loverushka_app.domain.model.Address
import ru.itis.loverushka_app.domain.repository.AddressRepository

class AddressRepositoryImpl : AddressRepository {

    override suspend fun addAddress(address: Address) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAddress(addressId: Int) {
        TODO("Not yet implemented")
    }
}
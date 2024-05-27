package ru.itis.loverushka_app.domain.repository

import ru.itis.loverushka_app.domain.model.Address

interface AddressRepository {
    // TODO
    suspend fun addAddress(address: Address)
    suspend fun deleteAddress(addressId: Int)
}
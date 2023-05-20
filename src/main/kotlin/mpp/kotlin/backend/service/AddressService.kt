package mpp.kotlin.backend.service

import domain.Address
import mpp.kotlin.backend.repository.AddressRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AddressService(
    private val addressRepository: AddressRepository
) {
    fun findAll(): MutableIterable<Address> {
        return addressRepository.findAll()
    }

    fun findOne(id: Int): Address {
        val optional: Optional<Address> = addressRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Address not found")
        }
    }

}
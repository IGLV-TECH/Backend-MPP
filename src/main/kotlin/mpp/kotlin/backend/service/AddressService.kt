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

    fun findById(id: Int): Address {
        val optional: Optional<Address> = addressRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Address not found")
        }
    }

    fun findOne(address: Address): Int {
        var id = 0
        for (a in addressRepository.findAll()) {
            if (a.getId() > id) id = a.getId()
            if (a.myEquals(address)) return a.getId()
        }
        addressRepository.save(address)
        return id + 1
    }

}
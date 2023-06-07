package mpp.kotlin.backend.service

import domain.Address
import mpp.kotlin.backend.repository.AddressRepository
import org.springframework.stereotype.Service
import java.util.*
import mu.KotlinLogging

@Service
class AddressService(
    val addressRepository: AddressRepository
) {
    private val logger = KotlinLogging.logger {}

    fun findAll(): List<Address> {
        logger.info { "Retrieving all addresses" }
        val addresses = addressRepository.findAll()
        return addresses.sortedBy { it.getId() }
    }

    fun findById(id: Int): Address {
        logger.info { "Retrieving address by ID: $id" }
        val optional: Optional<Address> = addressRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            logger.error { "Address not found for ID: $id" }
            throw RuntimeException("Address not found")
        }
    }

    fun findOne(address: Address): Int {
        logger.info { "Retrieving one address" }
        for (a in addressRepository.findAll()) {
            if (a.myEquals(address)) {
                logger.info { "Address found with ID: ${a.getId()}" }
                return a.getId()
            }
        }
        val savedAddress = addressRepository.save(address)
        logger.info { "Address not found, but saved with ID: ${savedAddress.getId()}" }
        return savedAddress.getId()
    }
}

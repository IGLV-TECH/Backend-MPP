package mpp.kotlin.backend.controller

import mpp.kotlin.backend.repository.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import domain.Address as Address

@RestController
@RequestMapping("/addresses")
class AddressController {

    @Autowired
    private lateinit var addressRepository: AddressRepository

    @GetMapping("/all")
    fun listAll(): MutableIterable<Address> {
        return addressRepository.findAll()
    }
}
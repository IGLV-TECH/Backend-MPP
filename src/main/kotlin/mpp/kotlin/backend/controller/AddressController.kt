package mpp.kotlin.backend.controller

import mpp.kotlin.backend.service.AddressService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import domain.Address as Address

@RestController
@RequestMapping("/addresses")
class AddressController {

    @Autowired
    private lateinit var addressService: AddressService

    @GetMapping()
    fun listAll(): MutableIterable<Address> {
        return addressService.findAll()
    }
}
package mpp.kotlin.backend.controller

import domain.Client
import mpp.kotlin.backend.repository.ClientRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientRepository: ClientRepository

    @GetMapping("/all")
    fun listAll(): List<Client> {
        return clientRepository.findAll()
    }
}
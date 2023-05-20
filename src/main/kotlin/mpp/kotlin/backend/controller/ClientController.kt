package mpp.kotlin.backend.controller

import domain.Client
import mpp.kotlin.backend.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @GetMapping("/all")
    fun listAll(): MutableIterable<Client> {
        return clientService.findAll()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Client {
        return clientService.findOne(id)
    }
}
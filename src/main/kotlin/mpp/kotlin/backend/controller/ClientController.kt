package mpp.kotlin.backend.controller

import domain.Client
import mpp.kotlin.backend.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @GetMapping("")
    fun listAll(): MutableIterable<Client> {
        return clientService.findAll()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Client {
        return clientService.findOne(id)
    }

    @PostMapping("")
    fun addClient(@RequestBody client: Client): ResponseEntity<*> {
        this.clientService.addClient(client)
        return ResponseEntity.ok().build<Any>()
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Int, @RequestBody client: Client): ResponseEntity<*> {
        this.clientService.updateClient(client)
        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: Int): ResponseEntity<*> {
        this.clientService.deleteById(id)
        return ResponseEntity.ok().build<Any>()
    }
}
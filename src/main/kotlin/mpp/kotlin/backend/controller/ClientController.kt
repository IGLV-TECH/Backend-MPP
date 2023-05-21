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

    @GetMapping("/all")
    fun listAll(): MutableIterable<Client> {
        return clientService.findAll()
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Client {
        return clientService.findOne(id)
    }

    @GetMapping("")
    fun getClients(
        @RequestParam("start", defaultValue = "0") start: Int,
        @RequestParam("count", defaultValue = "5") count: Int
    ): List<Client> {
        return this.clientService.getAll(start, count)
    }

    @PostMapping("")
    fun addClient(@RequestBody client: Client): ResponseEntity<*> {
        this.clientService.add(client)
        return ResponseEntity.ok().build<Any>()
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Int, @RequestBody client: Client): ResponseEntity<*> {
        this.clientService.update(client)
        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: Int): ResponseEntity<*> {
        this.clientService.deleteById(id)
        return ResponseEntity.ok().build<Any>()
    }
}
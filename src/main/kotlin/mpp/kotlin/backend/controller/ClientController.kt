package mpp.kotlin.backend.controller

import domain.Client
import mpp.kotlin.backend.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService
    @Autowired
    private lateinit var addressController: AddressController

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int, @RequestParam("count") count: Int
    ): List<Client> {
        val all = clientService.findAll().toList()
        val endIndex = (start + count).coerceAtMost(all.size)
        return all.subList(start, endIndex)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Client {
        return clientService.findOne(id)
    }

//    @PostMapping()
//    fun save(@RequestBody client: Client) {
//        clientService.save(client)
//    }
//
//    @PutMapping("/{id}")
//    fun update(@RequestBody client: Client) {
//        clientService.update(client)
//    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        clientService.delete(id);

//    @GetMapping("")
//    fun getClients(
//        @RequestParam("start", defaultValue = "0") start: Int,
//        @RequestParam("count", defaultValue = "5") count: Int
//    ): List<Client> {
//        return this.clientService.getAll(start, count)
//    }

//    @PostMapping("")
//    fun addClient(@RequestBody client: Client): ResponseEntity<*> {
//        this.clientService.add(client)
//        return ResponseEntity.ok().build<Any>()
//    }

//    @PutMapping("/{id}")
//    fun updateClient(@PathVariable id: Int, @RequestBody client: Client): ResponseEntity<*> {
//        this.clientService.update(client)
//        return ResponseEntity.ok().build<Any>()
//    }

//    @DeleteMapping("/{id}")
//    fun deleteOne(@PathVariable id: Int): ResponseEntity<*> {
//        this.clientService.deleteById(id)
//        return ResponseEntity.ok().build<Any>()
    }
}
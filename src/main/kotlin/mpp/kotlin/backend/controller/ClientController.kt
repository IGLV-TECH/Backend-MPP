package mpp.kotlin.backend.controller

import domain.Client
import mpp.kotlin.backend.service.AddressService
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
    private lateinit var addressService: AddressService

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int, @RequestParam("count") count: Int
    ): List<Client> {
        val all = clientService.findAll().toList()
        val endIndex = (start + count).coerceAtMost(all.size)
        return all.subList(start, endIndex)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Client {
        return clientService.findById(id)
    }

    @PostMapping()
    fun save(@RequestBody request: ClientRequest) {
        val address = request.address
        val id = addressService.findOne(address)
        var client = Client(
            request.lastName,
            request.firstName,
            request.phoneNumber,
            request.email,
            request.password,
            request.balance,
            addressService.findById(id)
        )
        clientService.save(client)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: ClientRequest) {
        val address = request.address
        val idAddress = addressService.findOne(address)
        var client = Client(
            request.lastName,
            request.firstName,
            request.phoneNumber,
            request.email,
            request.password,
            request.balance,
            addressService.findById(idAddress)
        )
        client.setId(id)
        clientService.update(client)
    }

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
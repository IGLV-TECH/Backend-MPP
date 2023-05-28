package mpp.kotlin.backend.controller

import NotFoundException
import TokenProvider
import UnauthorizedException
import BadRequestException
import domain.Client
import mpp.kotlin.backend.service.AddressService
import mpp.kotlin.backend.service.ClientService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import kotlin.math.log

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @Autowired
    private lateinit var addressService: AddressService
    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val client = clientService.login(loginRequest.email, loginRequest.password)
        return if (client != null) {
            val token = tokenProvider.generateToken(loginRequest.email)
            mapOf("token" to token, "client" to client)
        } else {
            throw UnauthorizedException("Invalid data")
        }
    }

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Client> {
        if (tokenProvider.validateToken(token)) {
            val all = clientService.findAll().toList()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Client {
        if (tokenProvider.validateToken(token)) {
            try {
                return clientService.findById(id)
            } catch (e: RuntimeException) {
                throw NotFoundException("Client not found")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: ClientRequest, @RequestHeader("Authorization") token: String) {
        if (tokenProvider.validateToken(token)) {
            val address = request.address
            println(address)
            val id = addressService.findOne(address)
            println(id)
            val client = Client(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                request.password,
                request.balance,
                addressService.findById(id)
            )
            println(client)
            try {
                clientService.save(client)
            } catch (e: RuntimeException) {
                throw BadRequestException("Error saving client")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int, @RequestBody request: ClientRequest, @RequestHeader("Authorization") token: String
    ) {
        if (tokenProvider.validateToken(token)) {
            val address = request.address
            val idAddress = addressService.findOne(address)
            val client = Client(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                request.password,
                request.balance,
                addressService.findById(idAddress)
            )
            client.setId(id)
            try {
                clientService.update(client)
            } catch (e: RuntimeException) {
                throw NotFoundException("Client not found")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, @RequestHeader("Authorization") token: String) {
        if (tokenProvider.validateToken(token)) {
            try {
                clientService.delete(id)
            } catch (e: RuntimeException) {
                throw NotFoundException("Client not found")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }
}

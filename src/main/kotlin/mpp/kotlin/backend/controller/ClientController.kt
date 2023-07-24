package mpp.kotlin.backend.controller

import BadRequestException
import NotFoundException
import TokenProvider
import UnauthorizedException
import domain.Client
import mpp.kotlin.backend.service.AddressService
import mpp.kotlin.backend.service.ClientService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.security.MessageDigest
import java.util.*
import javax.xml.bind.DatatypeConverter

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/clients")
class ClientController {

    @Autowired
    private lateinit var clientService: ClientService

    @Autowired
    private lateinit var addressService: AddressService

    private val tokenProvider: TokenProvider = TokenProvider

    private val logger = KotlinLogging.logger {}

    private fun encryptPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(password.toByteArray())
        return DatatypeConverter.printHexBinary(hashBytes).lowercase(Locale.getDefault())
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val client = clientService.login(loginRequest.email, encryptPassword(loginRequest.password))
        if (client != null) {
            val token = tokenProvider.generateToken(loginRequest.email, "client")
            return mapOf("token" to token, "client" to client)
        } else {
            logger.error { "Invalid data in login" }
            throw UnauthorizedException("Invalid data")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String) {
        logger.info {
            "Logging out ${tokenProvider.getRoleFromToken(token)} with email: ${
                tokenProvider.getEmailFromToken(
                    token
                )
            }"
        }
        tokenProvider.invalidateToken(token)
    }

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Client> {
        logger.info { "Listing all clients" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            val all = clientService.findAll()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else {
            logger.error { "Unauthorized access to list all clients" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Client {
        logger.info { "Finding client by ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            try {
                return clientService.findById(id)
            } catch (e: RuntimeException) {
                logger.error { "Client not found: $e" }
                throw NotFoundException("Client not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to find client by ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: ClientRequest, @RequestHeader("Authorization") token: String) {
        logger.info { "Saving a new client: ${request.firstName} ${request.lastName}" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            val address = request.address
            val id = addressService.findOne(address)
            val client = Client(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                encryptPassword(request.password),
                request.balance,
                addressService.findById(id)
            )
            try {
                clientService.save(client)
                logger.info { "Client saved successfully: ${client.getFirstName()} ${client.getLastName()}: ${client.getEmail()}" }
            } catch (e: RuntimeException) {
                logger.error { "Error saving client: $e" }
                throw BadRequestException("Error saving client: $e")
            }
        } else {
            logger.error { "Unauthorized access to save a new client" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int, @RequestBody request: ClientRequest, @RequestHeader("Authorization") token: String
    ) {
        logger.info { "Updating client with ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            val old = clientService.findById(id)
            var password = old.getPassword()
            if (request.password != "") password = encryptPassword(request.password)
            val client = Client(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                password,
                request.balance,
                request.address
            )
            client.setId(id)
            try {
                clientService.update(client)
                logger.info { "Client updated successfully: ${client.getFirstName()} ${client.getLastName()}: ${client.getEmail()}" }
            } catch (e: RuntimeException) {
                logger.error { "Client not found: $e" }
                throw NotFoundException("Client not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to update client with ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, @RequestHeader("Authorization") token: String) {
        logger.info { "Deleting client with ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            try {
                clientService.delete(id)
                logger.info { "Client deleted successfully: ID $id" }
            } catch (e: RuntimeException) {
                logger.error { "Client not found: $e" }
                throw NotFoundException("Client not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to delete client with ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PutMapping("/{id}/withdraw")
    fun withdraw(
        @PathVariable id: Int, @RequestParam amount: Float, @RequestHeader("Authorization") token: String
    ) {
        logger.info { "Starting withdraw client ID $id, amount $amount" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "client") {
            clientService.withdraw(id, amount)
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }
}

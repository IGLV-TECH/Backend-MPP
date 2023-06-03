package mpp.kotlin.backend.service

import domain.Client
import mpp.kotlin.backend.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*
import mu.KotlinLogging

@Service
class ClientService(
    val clientRepository: ClientRepository
) {
    private val logger = KotlinLogging.logger {}

    fun login(email: String, password: String): Client? {
        logger.info { "Logging in client with email: $email" }
        val client = clientRepository.findAll().find { c ->
            c.getEmail() == email && c.getPassword() == password
        }
        if (client != null) {
            logger.info { "Client login successful: $email" }
        } else {
            logger.error { "Client login failed: $email" }
        }
        return client
    }

    fun findAll(): MutableIterable<Client> {
        logger.info { "Retrieving all clients" }
        return clientRepository.findAll()
    }

    fun findById(id: Int): Client {
        logger.info { "Retrieving client by ID: $id" }
        val optional: Optional<Client> = clientRepository.findById(id)
        return optional.orElseThrow { RuntimeException("Client not found") }
    }

    fun save(client: Client) {
        logger.info { "Saving client with email: ${client.getEmail()}" }
        for (c in clientRepository.findAll()) {
            if (c.getEmail() == client.getEmail()) {
                throw RuntimeException("Email already used")
            }
        }
        clientRepository.save(client)
    }

    fun update(client: Client) {
        logger.info { "Updating client with ID: ${client.getId()}" }
        if (!clientRepository.existsById(client.getId())) {
            throw RuntimeException("Client not found")
        }
        clientRepository.save(client)
    }

    fun delete(id: Int) {
        logger.info { "Deleting client with ID: $id" }
        if (!clientRepository.existsById(id)) {
            throw RuntimeException("Client not found")
        }
        clientRepository.deleteById(id)
    }

    fun withdraw(id: Int, amount: Float) {
        val client = findById(id)
        client.setBalance(client.getBalance() - amount)
        clientRepository.save(client)
    }

//    /**
//     * Add amount to the balance of the client with clientId
//     * @param amount: negative/positive/==0 float value
//     * @param clientId: int
//     */
//    fun addToBalance(amount: Float, clientId: Int) {
//        logger.info { "Adding amount $amount to the balance of client with ID: $clientId" }
//        var client = this.findById(clientId)
//        client.setBalance(client.getBalance() + amount)
//        this.clientRepository.save(client)
//    }
}

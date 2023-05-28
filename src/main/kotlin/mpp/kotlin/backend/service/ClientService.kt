package mpp.kotlin.backend.service

import domain.Address
import domain.Client
import mpp.kotlin.backend.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class ClientService(
    private val clientRepository: ClientRepository
) {
    fun login(email: String, password: String): Client? {
        val client = clientRepository.findAll().find { c ->
            c.getEmail() == email && c.getPassword() == password
        }
        return client
    }

    fun findAll(): MutableIterable<Client> {
        return clientRepository.findAll()
    }

    fun findById(id: Int): Client {
        val optional: Optional<Client> = clientRepository.findById(id)
        return optional.orElseThrow { RuntimeException("Client not found") }
    }

    fun save(client: Client) {
        for (c in clientRepository.findAll()) if (c.getEmail() == client.getEmail()) {
            throw RuntimeException("Email already used")
        }
        clientRepository.save(client)
    }

    fun update(client: Client) {
        if (!clientRepository.existsById(client.getId())) {
            throw RuntimeException("Client not found")
        }
        clientRepository.save(client)
    }

    fun delete(id: Int) {
        if (!clientRepository.existsById(id)) {
            throw RuntimeException("Client not found")
        }
        clientRepository.deleteById(id)
    }

    /**
     * Add amount to the balance of the client with clientId
     * @param amount: negative/positive/==0 float value
     * @param clientId: int
     */
    fun addToBalance(amount: Float, clientId: Int) {
        var client = this.findById(clientId);
        client.setBalance(client.getBalance() + amount)
        this.clientRepository.save(client)
    }
}
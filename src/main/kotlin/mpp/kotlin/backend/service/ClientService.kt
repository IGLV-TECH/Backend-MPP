package mpp.kotlin.backend.service

import domain.Client
import mpp.kotlin.backend.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@Service
class ClientService(
    private val clientRepository: ClientRepository
) {
    fun findAll(): MutableIterable<Client> {
        return clientRepository.findAll()
    }

    fun findOne(id: Int): Client {
        val optional: Optional<Client> = clientRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Client not found")
        }
    }

    fun getAll(start: Int, count: Int): List<Client> {
        val pageable: Pageable = PageRequest.of(start, count)
        return clientRepository.findAllClients(pageable)
    }

    fun add(client: Client){
        this.clientRepository.save(client)
    }

    fun update(client: Client){
        this.clientRepository.save(client)
    }

    fun deleteById(id: Int){
        this.clientRepository.deleteById(id)
    }

    /**
     * Add amount to the balance of the client with clientId
     * @param amount: negative/positive/==0 float value
     * @param clientId: int
     */
    fun addToBalance(amount: Float, clientId: Int){
        var client = this.findOne(clientId);
        client.setBalance(client.getBalance() + amount)
        this.clientRepository.save(client)
    }
}
package mpp.kotlin.backend.service

import domain.Client
import mpp.kotlin.backend.repository.ClientRepository
import org.springframework.stereotype.Service
import java.util.*

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

    fun save(client: Client) {
        if(clientRepository.findById(client.getId()).isEmpty) {
            clientRepository.save(client)
        } else {
            throw RuntimeException("Client already added")
        }
    }

    fun update(client: Client) {
        if(clientRepository.findById(client.getId()).isPresent) {
            clientRepository.save(client)
        } else {
            throw RuntimeException("Client non existent")
        }
    }

    fun delete(id: Int) {
        var client = findOne(id)
        clientRepository.delete(client)
    }
}
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

    fun addClient(client: Client){
        this.clientRepository.save(client)
    }

    fun updateClient(client: Client){
        this.clientRepository.save(client)
    }

    fun deleteById(id: Int){
        this.clientRepository.deleteById(id)
    }

}
package mpp.kotlin.backend.repository

import domain.Client
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@Repository
interface ClientRepository: CrudRepository<Client, Int> {
    @Query("SELECT c FROM Client c ORDER BY c.id ASC")
    fun findAllClients(pageable: Pageable): List<Client>
}
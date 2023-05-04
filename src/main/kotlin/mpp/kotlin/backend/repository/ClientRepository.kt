package mpp.kotlin.backend.repository

import domain.Client
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository: CrudRepository<Client, Int> {
}
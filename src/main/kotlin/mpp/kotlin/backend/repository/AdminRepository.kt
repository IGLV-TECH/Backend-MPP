package mpp.kotlin.backend.repository

import domain.Admin
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository : CrudRepository<Admin, Int> {}
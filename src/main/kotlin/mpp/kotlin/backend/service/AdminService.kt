package mpp.kotlin.backend.service

import domain.Admin
import domain.Client
import domain.Employee
import mpp.kotlin.backend.repository.AdminRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdminService(
    private val adminRepository: AdminRepository
) {
    fun login(email: String, password: String): Admin? {
        val admin = adminRepository.findAll().find { a ->
            a.getEmail() == email && a.getPassword() == password
        }
        return admin
    }

    fun findAll(): MutableIterable<Admin> {
        return adminRepository.findAll()
    }

    fun findById(id: Int): Admin {
        val optional: Optional<Admin> = adminRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Admin not found")
        }
    }

}
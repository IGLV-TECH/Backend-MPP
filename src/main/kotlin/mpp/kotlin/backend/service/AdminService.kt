package mpp.kotlin.backend.service

import domain.Admin
import mpp.kotlin.backend.repository.AdminRepository
import org.springframework.stereotype.Service

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
}
package mpp.kotlin.backend.service

import domain.Admin
import mpp.kotlin.backend.repository.AdminRepository
import org.springframework.stereotype.Service
import mu.KotlinLogging

@Service
class AdminService(
    val adminRepository: AdminRepository
) {
    private val logger = KotlinLogging.logger {}

    fun login(email: String, password: String): Admin? {
        logger.info { "Logging in admin with email: $email" }
        val admin = adminRepository.findAll().find { a ->
            a.getEmail() == email && a.getPassword() == password
        }
        if (admin != null) {
            logger.info { "Admin login successful: $email" }
        } else {
            logger.error { "Admin login failed: $email" }
        }
        return admin
    }
}

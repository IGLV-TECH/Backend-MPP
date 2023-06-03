package mpp.kotlin.backend.controller

import TokenProvider
import UnauthorizedException
import mpp.kotlin.backend.service.AdminService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/admins")
class AdminController {

    @Autowired
    private lateinit var adminService: AdminService

    private val tokenProvider: TokenProvider = TokenProvider

    private val logger = KotlinLogging.logger {}

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val admin = adminService.login(loginRequest.email, loginRequest.password)
        if (admin != null) {
            val token = tokenProvider.generateToken(loginRequest.email, "admin")
            return mapOf("token" to token, "admin" to admin)
        } else {
            logger.error { "Invalid data in login" }
            throw UnauthorizedException("Invalid data")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String) {
        logger.info { "Logging out ${tokenProvider.getRoleFromToken(token)} with email: ${tokenProvider.getEmailFromToken(token)}" }
        tokenProvider.invalidateToken(token)
    }
}
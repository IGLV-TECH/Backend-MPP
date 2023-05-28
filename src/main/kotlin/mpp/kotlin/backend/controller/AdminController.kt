package mpp.kotlin.backend.controller

import TokenProvider
import UnauthorizedException
import mpp.kotlin.backend.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/admins")
class AdminController {

    @Autowired
    private lateinit var adminService: AdminService
    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val admin = adminService.login(loginRequest.email, loginRequest.password)
        return if (admin != null) {
            val token = tokenProvider.generateToken(loginRequest.email)
            mapOf("token" to token, "admin" to admin)
        } else {
            throw UnauthorizedException("Invalid data")
        }
    }
}
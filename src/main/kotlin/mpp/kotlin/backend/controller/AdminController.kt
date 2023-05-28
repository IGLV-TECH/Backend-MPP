package mpp.kotlin.backend.controller

import TokenProvider
import UnauthorizedException
import domain.Admin
import mpp.kotlin.backend.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.HttpClientErrorException.Unauthorized

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/admins")
class AdminController {

    @Autowired
    private lateinit var adminService: AdminService
    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping()
    fun listAll(@RequestHeader("Authorization") token: String): MutableIterable<Admin> {
        if(tokenProvider.validateToken(token))
            return adminService.findAll()
        else
            throw UnauthorizedException("Invalid token")
    }
}
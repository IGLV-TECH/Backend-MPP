package mpp.kotlin.backend.controller

import domain.Admin
import mpp.kotlin.backend.repository.AdminRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admins")
class AdminController {

    @Autowired
    private lateinit var adminRepository: AdminRepository

    @GetMapping("/all")
    fun listAll(): MutableIterable<Admin> {
        return adminRepository.findAll()
    }
}
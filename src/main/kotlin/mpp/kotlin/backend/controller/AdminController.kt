package mpp.kotlin.backend.controller

import domain.Admin
import mpp.kotlin.backend.service.AdminService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/admins")
class AdminController {

    @Autowired
    private lateinit var adminService: AdminService

    @GetMapping()
    fun listAll(): MutableIterable<Admin> {
        return adminService.findAll()
    }
}
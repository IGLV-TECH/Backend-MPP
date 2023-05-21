package mpp.kotlin.backend.controller

import domain.Employee
import mpp.kotlin.backend.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @GetMapping("/all")
    fun listAll(): MutableIterable<Employee> {
        return employeeService.findAll()
    }
}
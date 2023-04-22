package mpp.kotlin.backend.controller

import domain.Employee
import mpp.kotlin.backend.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @GetMapping("/all")
    fun listAll(): List<Employee> {
        return employeeRepository.findAll()
    }
}
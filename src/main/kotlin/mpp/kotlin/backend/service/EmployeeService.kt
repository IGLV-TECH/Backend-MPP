package mpp.kotlin.backend.service

import domain.Employee
import mpp.kotlin.backend.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*
import mu.KotlinLogging

@Service
class EmployeeService(
    val employeeRepository: EmployeeRepository
) {
    private val logger = KotlinLogging.logger {}

    fun login(email: String, password: String): Employee? {
        logger.info { "Logging in employee with email: $email" }
        val employee = employeeRepository.findAll().find { e ->
            e.getEmail() == email && e.getPassword() == password
        }
        if (employee != null) {
            logger.info { "Employee login successful: $email" }
        } else {
            logger.error { "Employee login failed: $email" }
        }
        return employee
    }

    fun findAll(): MutableIterable<Employee> {
        logger.info { "Retrieving all employees" }
        return employeeRepository.findAll()
    }

    fun findById(id: Int): Employee {
        logger.info { "Retrieving employee by ID: $id" }
        val optional: Optional<Employee> = employeeRepository.findById(id)
        return optional.orElseThrow { RuntimeException("Employee not found") }
    }

    fun save(employee: Employee) {
        logger.info { "Saving employee with email: ${employee.getEmail()}" }
        for (e in employeeRepository.findAll()) {
            if (e.getEmail() == employee.getEmail()) {
                throw RuntimeException("Email already used")
            }
        }
        employeeRepository.save(employee)
    }

    fun update(employee: Employee) {
        logger.info { "Updating employee with ID: ${employee.getId()}" }
        if (!employeeRepository.existsById(employee.getId())) {
            throw RuntimeException("Employee not found")
        }
        employeeRepository.save(employee)
    }

    fun delete(id: Int) {
        logger.info { "Deleting employee with ID: $id" }
        if (!employeeRepository.existsById(id)) {
            throw RuntimeException("Employee not found")
        }
        employeeRepository.deleteById(id)
    }
}

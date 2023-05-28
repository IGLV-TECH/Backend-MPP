package mpp.kotlin.backend.service

import domain.Employee
import mpp.kotlin.backend.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    fun login(email: String, password: String): Employee? {
        val employee = employeeRepository.findAll().find { e ->
            e.getEmail() == email && e.getPassword() == password
        }
        return employee
    }

    fun findAll(): MutableIterable<Employee> {
        return employeeRepository.findAll()
    }

    fun findById(id: Int): Employee {
        val optional: Optional<Employee> = employeeRepository.findById(id)
        return optional.orElseThrow { RuntimeException("Employee not found") }
    }

    fun save(employee: Employee) {
        for (e in employeeRepository.findAll()) if (e.getEmail() == employee.getEmail()) {
            throw RuntimeException("Email already used")
        }
        employeeRepository.save(employee)
    }

    fun update(employee: Employee) {
        if (!employeeRepository.existsById(employee.getId())) {
            throw RuntimeException("Employee not found")
        }
        employeeRepository.save(employee)
    }

    fun delete(id: Int) {
        if (!employeeRepository.existsById(id)) {
            throw RuntimeException("Employee not found")
        }
        employeeRepository.deleteById(id)
    }
}
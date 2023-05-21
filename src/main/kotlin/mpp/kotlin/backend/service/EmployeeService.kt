package mpp.kotlin.backend.service

import domain.Employee
import mpp.kotlin.backend.repository.EmployeeRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    fun findAll(): MutableIterable<Employee> {
        return employeeRepository.findAll()
    }

    fun findOne(id: Int): Employee {
        val optional: Optional<Employee> = employeeRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Employee not found")
        }
    }

    fun save(employee: Employee) {
        if(employeeRepository.findById(employee.getId()).isEmpty) {
            employeeRepository.save(employee)
        } else {
            throw RuntimeException("Employee already added")
        }
    }

    fun update(employee: Employee) {
        if(employeeRepository.findById(employee.getId()).isPresent) {
            employeeRepository.save(employee)
        } else {
            throw RuntimeException("Employee not found")
        }
    }

    fun delete(id: Int) {
        var employee = findOne(id)
        employeeRepository.delete(employee)
    }
}
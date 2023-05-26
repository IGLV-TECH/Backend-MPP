package mpp.kotlin.backend.service

import domain.Client
import domain.Employee
import mpp.kotlin.backend.repository.EmployeeRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository
) {
    fun findAll(): MutableIterable<Employee> {
        return employeeRepository.findAll()
    }

    fun findById(id: Int): Employee {
        val optional: Optional<Employee> = employeeRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Employee not found")
        }
    }

    fun save(employee: Employee) {
        employeeRepository.save(employee)
    }

    fun update(employee: Employee) {
        employeeRepository.save(employee)
    }

    fun delete(id: Int) {
        var employee = findById(id)
        employeeRepository.delete(employee)
    }

//    fun getAll(start: Int, count: Int): List<Employee> {
//        val pageable: Pageable = PageRequest.of(start, count)
//        return employeeRepository.findAllEmployees(pageable)
//    }
//
//    fun add(employee: Employee){
//        this.employeeRepository.save(employee)
//    }
//
//    fun update(employee: Employee){
//        this.employeeRepository.save(employee)
//    }
//
//    fun deleteById(id: Int){
//        this.employeeRepository.deleteById(id)
//    }
}
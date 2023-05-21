package mpp.kotlin.backend.controller

import domain.Client
import domain.Employee
import mpp.kotlin.backend.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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


    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Employee {
        return employeeService.findOne(id)
    }

    @GetMapping("")
    fun getEmployees(
        @RequestParam("start", defaultValue = "0") start: Int,
        @RequestParam("count", defaultValue = "5") count: Int
    ): List<Employee> {
        return this.employeeService.getAll(start, count)
    }

    @PostMapping("")
    fun addClient(@RequestBody employee: Employee): ResponseEntity<*> {
        this.employeeService.add(employee)
        return ResponseEntity.ok().build<Any>()
    }

    @PutMapping("/{id}")
    fun updateClient(@PathVariable id: Int, @RequestBody employee: Employee): ResponseEntity<*> {
        this.employeeService.update(employee)
        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/{id}")
    fun deleteOne(@PathVariable id: Int): ResponseEntity<*> {
        this.employeeService.deleteById(id)
        return ResponseEntity.ok().build<Any>()
    }
}
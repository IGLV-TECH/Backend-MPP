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
    @Autowired
    private lateinit var addressController: AddressController

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int, @RequestParam("count") count: Int
    ): List<Employee> {
        val all = employeeService.findAll().toList()
        val endIndex = (start + count).coerceAtMost(all.size)
        return all.subList(start, endIndex)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Employee {
        return employeeService.findOne(id)
    }

//    @PostMapping()
//    fun save(@RequestBody employee: Employee) {
//        employeeService.save(employee)
//    }
//
//    @PutMapping("/{id}")
//    fun update(@RequestBody employee: Employee) {
//        employeeService.update(employee)
//    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        employeeService.delete(id);
    }

//    @GetMapping("")
//    fun getEmployees(
//        @RequestParam("start", defaultValue = "0") start: Int,
//        @RequestParam("count", defaultValue = "5") count: Int
//    ): List<Employee> {
//        return this.employeeService.getAll(start, count)
//    }
//
//    @PostMapping("")
//    fun addClient(@RequestBody employee: Employee): ResponseEntity<*> {
//        this.employeeService.add(employee)
//        return ResponseEntity.ok().build<Any>()
//    }
//
//    @PutMapping("/{id}")
//    fun updateClient(@PathVariable id: Int, @RequestBody employee: Employee): ResponseEntity<*> {
//        this.employeeService.update(employee)
//        return ResponseEntity.ok().build<Any>()
//    }
//
//    @DeleteMapping("/{id}")
//    fun deleteOne(@PathVariable id: Int): ResponseEntity<*> {
//        this.employeeService.deleteById(id)
//        return ResponseEntity.ok().build<Any>()
//    }
}
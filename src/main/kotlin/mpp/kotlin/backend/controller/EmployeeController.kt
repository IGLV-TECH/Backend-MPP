package mpp.kotlin.backend.controller

import domain.Employee
import mpp.kotlin.backend.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
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
}
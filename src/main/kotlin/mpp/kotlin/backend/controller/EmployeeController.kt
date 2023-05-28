package mpp.kotlin.backend.controller

import BadRequestException
import NotFoundException
import TokenProvider
import UnauthorizedException
import domain.Employee
import mpp.kotlin.backend.service.AddressService
import mpp.kotlin.backend.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/employees")
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var addressService: AddressService

    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val employee = employeeService.login(loginRequest.email, loginRequest.password)
        return if (employee != null) {
            val token = tokenProvider.generateToken(loginRequest.email)
            mapOf("token" to token, "employee" to employee)
        } else {
            throw UnauthorizedException("Invalid data")
        }
    }

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Employee> {
        if (tokenProvider.validateToken(token)) {
            val all = employeeService.findAll().toList()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else throw UnauthorizedException("Invalid token")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Employee {
        if (tokenProvider.validateToken(token)) {
            try {
                return employeeService.findById(id)
            } catch (e: RuntimeException) {
                throw NotFoundException("Employee not found")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: EmployeeRequest, @RequestHeader("Authorization") token: String) {
        if (tokenProvider.validateToken(token)) {
            val address = request.address
            val id = addressService.findOne(address)
            var employee = Employee(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                request.password,
                addressService.findById(id)
            )
            try {
                employeeService.save(employee)
            } catch (e: RuntimeException) {
                throw BadRequestException("Error saving client: $e")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int, @RequestBody request: EmployeeRequest, @RequestHeader("Authorization") token: String
    ) {
        if (tokenProvider.validateToken(token)) {
            val address = request.address
            val idAddress = addressService.findOne(address)
            var employee = Employee(
                request.lastName,
                request.firstName,
                request.phoneNumber,
                request.email,
                request.password,
                addressService.findById(idAddress)
            )
            employee.setId(id)
            try {
                employeeService.update(employee)
            } catch (e: RuntimeException) {
                throw NotFoundException("Employee not found: $e")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, @RequestHeader("Authorization") token: String) {
        if (tokenProvider.validateToken(token)) {
            try {
                employeeService.delete(id)
            } catch (e: RuntimeException) {
                throw NotFoundException("Employee not found: $e")
            }
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }
}
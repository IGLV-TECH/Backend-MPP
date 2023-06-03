package mpp.kotlin.backend.controller

import BadRequestException
import NotFoundException
import TokenProvider
import UnauthorizedException
import domain.Employee
import mpp.kotlin.backend.service.AddressService
import mpp.kotlin.backend.service.EmployeeService
import mu.KotlinLogging
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

    private val tokenProvider: TokenProvider = TokenProvider

    private val logger = KotlinLogging.logger {}

    @GetMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Map<String, Any> {
        val employee = employeeService.login(loginRequest.email, loginRequest.password)
        return if (employee != null) {
            val token = tokenProvider.generateToken(loginRequest.email, "employee")
            mapOf("token" to token, "employee" to employee)
        } else {
            logger.error { "Invalid data in login" }
            throw UnauthorizedException("Invalid data")
        }
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") token: String) {
        logger.info { "Logging out ${tokenProvider.getRoleFromToken(token)} with email: ${tokenProvider.getEmailFromToken(token)}" }
        tokenProvider.invalidateToken(token)
    }

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Employee> {
        logger.info { "Listing all employees" }
        if (tokenProvider.validateToken(token)) {
            val all = employeeService.findAll().toList()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else {
            logger.error { "Unauthorized access to list all employees" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Employee {
        logger.info { "Finding employee by ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            try {
                return employeeService.findById(id)
            } catch (e: RuntimeException) {
                logger.error { "Employee not found" }
                throw NotFoundException("Employee not found")
            }
        } else {
            logger.error { "Unauthorized access to find employee by ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: EmployeeRequest, @RequestHeader("Authorization") token: String) {
        logger.info { "Saving a new employee: ${request.firstName} ${request.lastName}" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
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
                logger.info { "Employee saved successfully: ${employee.getFirstName()} ${employee.getLastName()}: ${employee.getEmail()}" }
            } catch (e: RuntimeException) {
                logger.error { "Error saving employee: $e" }
                throw BadRequestException("Error saving employee: $e")
            }
        } else {
            logger.error { "Unauthorized access to save a new employee" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int, @RequestBody request: EmployeeRequest, @RequestHeader("Authorization") token: String
    ) {
        logger.info { "Updating employee with ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
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
                logger.info { "Employee updated successfully: ${employee.getFirstName()} ${employee.getLastName()}: ${employee.getEmail()}" }
            } catch (e: RuntimeException) {
                logger.error { "Employee not found: $e" }
                throw NotFoundException("Employee not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to update employee with ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, @RequestHeader("Authorization") token: String) {
        logger.info { "Deleting employee with ID: $id" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "admin") {
            try {
                employeeService.delete(id)
                logger.info { "Employee deleted successfully: ID $id" }
            } catch (e: RuntimeException) {
                logger.error { "Employee not found: $e" }
                throw NotFoundException("Employee not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to delete employee with ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }
}

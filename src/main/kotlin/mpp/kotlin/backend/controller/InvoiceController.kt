package mpp.kotlin.backend.controller

import NotFoundException
import TokenProvider
import UnauthorizedException
import com.fasterxml.jackson.databind.ObjectMapper
import domain.*
import mpp.kotlin.backend.service.ClientService
import mpp.kotlin.backend.service.EmployeeService
import mpp.kotlin.backend.service.InvoiceService
import mpp.kotlin.backend.service.ItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/invoices")
class InvoiceController {

    @Autowired
    private lateinit var invoiceService: InvoiceService

    @Autowired
    private lateinit var clientService: ClientService

    @Autowired
    private lateinit var employeeService: EmployeeService

    @Autowired
    private lateinit var itemsService: ItemsService

    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Invoice> {
        println(tokenProvider.getRoleFromToken(token))
        if (tokenProvider.validateToken(token) && (tokenProvider.getRoleFromToken(token) == "client" || tokenProvider.getRoleFromToken(token) == "admin")
        ) {
            val all = invoiceService.findAll().toList()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else throw UnauthorizedException("Invalid token")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Invoice {
        println(tokenProvider.getRoleFromToken(token))
        if (tokenProvider.validateToken(token) && (tokenProvider.getRoleFromToken(token) == "client" || tokenProvider.getRoleFromToken(token) == "admin")
        ) try {
            return invoiceService.findById(id)
        } catch (e: RuntimeException) {
            throw NotFoundException("Client not found: $e")
        } else {
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: InvoiceRequest, @RequestHeader("Authorization") token: String) {
        println(tokenProvider.getRoleFromToken(token))
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "employee") {
            try {
                val client = clientService.findById(request.idClient)
                val employee = employeeService.findById(request.idEmployee)
                val list = mutableMapOf<Item, Int>()
                for (itemMap in request.listItems) {
                    val id = itemMap["id"]!!
                    val item = itemsService.findById(id)
                    println(item)
                    val number = itemMap["number"]!!
                    list[item] = number
                }
                invoiceService.addInvoice(client, employee, request.categoryType, request.penaltyPoints, list)
            } catch (e: RuntimeException) {
                throw NotFoundException("Error: $e")
            }
        } else throw UnauthorizedException("Invalid token")
    }
}

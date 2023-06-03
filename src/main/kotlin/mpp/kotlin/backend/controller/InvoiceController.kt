package mpp.kotlin.backend.controller

import NotFoundException
import TokenProvider
import UnauthorizedException
import domain.*
import mpp.kotlin.backend.service.ClientService
import mpp.kotlin.backend.service.EmployeeService
import mpp.kotlin.backend.service.InvoiceService
import mpp.kotlin.backend.service.ItemsService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
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

    private val tokenProvider: TokenProvider = TokenProvider

    private val logger = KotlinLogging.logger {}

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int,
        @RequestParam("count") count: Int,
        @RequestHeader("Authorization") token: String
    ): List<Invoice> {
        logger.info { "Listing all invoices" }
        if (tokenProvider.validateToken(token) && (tokenProvider.getRoleFromToken(token) == "client" || tokenProvider.getRoleFromToken(token) == "admin")) {
            val all = invoiceService.findAll().toList()
            val endIndex = (start + count).coerceAtMost(all.size)
            return all.subList(start, endIndex)
        } else {
            logger.error { "Unauthorized access to list all invoices" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int, @RequestHeader("Authorization") token: String): Invoice {
        logger.info { "Finding invoice by ID: $id" }
        if (tokenProvider.validateToken(token) && (tokenProvider.getRoleFromToken(token) == "client" || tokenProvider.getRoleFromToken(token) == "admin")) {
            try {
                return invoiceService.findById(id)
            } catch (e: RuntimeException) {
                logger.error { "Invoice not found: $e" }
                throw NotFoundException("Invoice not found: $e")
            }
        } else {
            logger.error { "Unauthorized access to find invoice by ID: $id" }
            throw UnauthorizedException("Invalid token")
        }
    }

    @PostMapping()
    fun save(@RequestBody request: InvoiceRequest, @RequestHeader("Authorization") token: String) {
        logger.info { "Saving a new invoice" }
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "employee") {
            try {
                val client = clientService.findById(request.idClient)
                val employee = employeeService.findById(request.idEmployee)
                val list = mutableMapOf<Item, Int>()
                for (itemMap in request.listItems) {
                    val id = itemMap["id"]!!
                    val item = itemsService.findById(id)
                    val number = itemMap["number"]!!
                    list[item] = number
                }
                invoiceService.addInvoice(client, employee, request.categoryType, request.penaltyPoints, list)
                logger.info { "Invoice saved successfully" }
            } catch (e: RuntimeException) {
                logger.error { "Error saving invoice: $e" }
                throw NotFoundException("Error: $e")
            }
        } else {
            logger.error { "Unauthorized access to save a new invoice" }
            throw UnauthorizedException("Invalid token")
        }
    }
}

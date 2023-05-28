package mpp.kotlin.backend.controller

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

    @GetMapping()
    fun listAll(
        @RequestParam("start") start: Int, @RequestParam("count") count: Int
    ): List<Invoice> {
        val all = invoiceService.findAll().toList()
        val endIndex = (start + count).coerceAtMost(all.size)
        return all.subList(start, endIndex)
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): Invoice {
        return invoiceService.findById(id)
    }

//    @GetMapping("")
//    fun getInvoices(
//        @RequestParam("start", defaultValue = "0") start: Int,
//        @RequestParam("count", defaultValue = "5") count: Int
//    ): List<Invoice> {
//        return this.invoiceService.getAll(start, count)
//    }

    @PostMapping()
    fun save(@RequestBody request: InvoiceRequest) {
        val client = clientService.findById(request.idClient)
        println(client)
        val employee = employeeService.findById(request.idEmployee)
        println(employee)
        val list = mutableMapOf<Item, Int>()
        for (itemMap in request.listItems) {
            val id = itemMap["id"]!!
            val item = itemsService.findById(id)
            println(item)
            val number = itemMap["number"]!!
            list[item] = number
        }
        invoiceService.addInvoice(client, employee, request.categoryType, request.penaltyPoints, list)
    }
}
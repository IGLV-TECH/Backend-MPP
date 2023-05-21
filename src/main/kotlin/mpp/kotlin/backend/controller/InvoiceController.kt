package mpp.kotlin.backend.controller

import domain.*
import mpp.kotlin.backend.service.ClientService
import mpp.kotlin.backend.service.EmployeeService
import mpp.kotlin.backend.service.InvoiceService
import mpp.kotlin.backend.service.ItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
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

//    @GetMapping("/{id}/items")
//    fun getItems(@PathVariable("id") id: Int): MutableIterable<Content> {
//        return invoiceService.getItems(id)
//    }

    @PostMapping()
    fun save(@RequestBody request: InvoiceRequest) {
        val client = clientService.findOne(request.idClient)
        println(client)
        val employee = employeeService.findOne(request.idEmployee)
        println(employee)
        val list = mutableMapOf<Item, Int>()
        for (itemMap in request.listItems) {
            val id = itemMap["id"]!!
            val item = itemsService.findOne(id)
            println(item)
            val number = itemMap["number"]!!
            list[item] = number
        }
        invoiceService.addInvoice(client, employee, request.categoryType, request.penaltyPoints, list)
    }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Int): Invoice {
        return invoiceService.findOne(id)
    }
}

data class InvoiceRequest(
    val idClient: Int,
    val idEmployee: Int,
    val categoryType: CategoryType,
    val penaltyPoints: Int,
    val listItems: List<Map<String, Int>>
)
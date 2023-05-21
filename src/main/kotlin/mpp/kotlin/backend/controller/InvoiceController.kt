package mpp.kotlin.backend.controller

import domain.CategoryType
import domain.Content
import domain.Invoice
import domain.Item
import mpp.kotlin.backend.service.ClientService
import mpp.kotlin.backend.service.EmployeeService
import mpp.kotlin.backend.service.InvoiceService
import mpp.kotlin.backend.service.ItemsService
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

    @GetMapping("/all")
    fun listAll(): MutableIterable<Invoice> {
        return invoiceService.findAll()
    }

    @GetMapping("/{id}/items")
    fun getItems(@PathVariable("id") id: Int): MutableIterable<Content> {
        return invoiceService.getItems(id)
    }

    @PostMapping()
    fun save(@RequestBody request: InvoiceRequest) {
        val client = clientService.findOne(request.client)
        println(client)
        val employee = employeeService.findOne(request.employee)
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
}

data class InvoiceRequest(
    val client: Int,
    val employee: Int,
    val categoryType: CategoryType,
    val penaltyPoints: Int,
    val listItems: List<Map<String, Int>>
)
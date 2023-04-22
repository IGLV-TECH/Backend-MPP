package mpp.kotlin.backend.controller

import domain.Invoice
import mpp.kotlin.backend.repository.InvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/invoices")
class InvoiceController {

    @Autowired
    private lateinit var invoiceRepository: InvoiceRepository

    @GetMapping("/all")
    fun listAll(): List<Invoice> {
        return invoiceRepository.findAll()
    }
}
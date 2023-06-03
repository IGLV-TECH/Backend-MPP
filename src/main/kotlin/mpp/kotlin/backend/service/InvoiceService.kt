package mpp.kotlin.backend.service

import domain.*
import mpp.kotlin.backend.repository.InvoiceRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*
import mu.KotlinLogging

@Service
class InvoiceService(
    val invoiceRepository: InvoiceRepository,
) {
    private val logger = KotlinLogging.logger {}

    fun findAll(): MutableIterable<Invoice> {
        logger.info { "Retrieving all invoices" }
        return invoiceRepository.findAll()
    }

    fun addInvoice(
        client: Client, employee: Employee, categoryType: CategoryType, penaltyPoints: Int, listItems: Map<Item, Int>
    ) {
        logger.info { "Adding invoice for client: ${client.getFirstName()} ${client.getLastName()}: ${client.getEmail()}" }

        var items: MutableList<Content> = mutableListOf()
        var payment = 0F
        var bonusPoints = 0
        for (it in listItems) {
            val item = it.key
            if (item != null) {
                payment += it.value * item.getPrice()
                bonusPoints += it.value
            }
        }
        var penalty = (penaltyPoints / bonusPoints) * payment
        payment -= penalty

        var invoice = Invoice(categoryType, payment, penalty, LocalDate.now(), client, employee)
        val savedInvoice = invoiceRepository.save(invoice)

        for (it in listItems) {
            val item = it.key
            if (item != null) {
                items.add(Content(ContentId(savedInvoice.getId(), item.getId()), savedInvoice, item, it.value))
            }
        }
        savedInvoice.items = items
        invoiceRepository.save(savedInvoice)

        client.setBalance(client.getBalance() + payment)
        logger.info { "Invoice added successfully for client: ${client.getFirstName()} ${client.getLastName()}: ${client.getEmail()}" }
    }

    fun findById(id: Int): Invoice {
        logger.info { "Retrieving invoice by ID: $id" }
        val optional: Optional<Invoice> = invoiceRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Invoice not found")
        }
    }
}

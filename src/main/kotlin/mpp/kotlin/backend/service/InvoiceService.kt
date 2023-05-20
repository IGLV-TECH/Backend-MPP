package mpp.kotlin.backend.service

import domain.*
import mpp.kotlin.backend.repository.InvoiceRepository
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class InvoiceService(
    private val invoiceRepository: InvoiceRepository
) {
    fun findAll(): MutableIterable<Invoice> {
        return invoiceRepository.findAll()
    }

    fun getItems(id: Int): MutableIterable<Content> {
        val optional = invoiceRepository.findById(id)
        return if (optional.isPresent) {
            val invoice = optional.get()
            return invoice.items
        } else {
            throw RuntimeException("Invoice not found")
        }
    }

    fun addInvoice(
        client: Client, employee: Employee, categoryType: CategoryType, penaltyPoints: Int, listItems: Map<Item, Int>
    ) {
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

        println(penalty)
        println(payment)

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
        println(savedInvoice)
    }
}
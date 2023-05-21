package mpp.kotlin.backend.service

import domain.*
import mpp.kotlin.backend.payments.PaymentsService
import mpp.kotlin.backend.repository.InvoiceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.*


@Service
class InvoiceService(
    private val invoiceRepository: InvoiceRepository,
    private val paymentsService: PaymentsService
) {
    fun findAll(): MutableIterable<Invoice> {
        return invoiceRepository.findAll()
    }

    fun findOne(id: Int): Invoice {
        val optional: Optional<Invoice> = invoiceRepository.findById(id)
        if (optional.isPresent) {
            return optional.get()
        } else {
            throw RuntimeException("Client not found")
        }
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

    fun getAll(start: Int, count: Int): List<Invoice> {
        val pageable: Pageable = PageRequest.of(start, count)
        return invoiceRepository.findAllInvoices(pageable)
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

        /* that part will start the payment procedure to the client */
        this.paymentsService.makePayment(client, payment)
    }
}
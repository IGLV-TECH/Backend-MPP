package mpp.kotlin.backend.service

import domain.*
import mpp.kotlin.backend.repository.InvoiceRepository
import mpp.kotlin.backend.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class InvoiceService(
    private val invoiceRepository: InvoiceRepository, private val itemRepository: ItemRepository
) {
    fun addInvoice(
        client: Client, employee: Employee, categoryType: CategoryType, penaltyPoints: Int, idItems: Map<Int, Int>
    ) {
        var items: MutableList<Item> = mutableListOf()
        var payment: Float = 0F
        var bonusPoints: Int = 0
        for (it in idItems) {
            val item = itemRepository.findById(it.key).orElse(null)
            if (item != null) {
                items.add(item)
                payment += it.value * item.getPrice()
                bonusPoints += it.value
            }
        }
        var penalty = (penaltyPoints / bonusPoints) * payment
        payment -= penalty
    }
}
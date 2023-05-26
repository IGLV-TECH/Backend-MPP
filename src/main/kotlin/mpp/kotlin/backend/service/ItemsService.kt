package mpp.kotlin.backend.service

import domain.CategoryType
import domain.Item
import mpp.kotlin.backend.repository.ItemRepository
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList

@Service
class ItemsService(
    val itemRepository: ItemRepository
) {
    fun findAll(): MutableIterable<Item> {
        return itemRepository.findAll()
    }

    fun findAllByCategory(categoryType: CategoryType): ArrayList<Map<String, String>> {
        var list = ArrayList<Map<String, String>>()
        for (item: Item in itemRepository.findAllByCategory(categoryType)) {
            var element = mutableMapOf<String, String>()
            element["id"] = item.getId().toString()
            element["title"] = item.getElement()
            list.add(element)
        }
        return list
    }

    fun findById(id: Int): Item {
        val optionalClient: Optional<Item> = itemRepository.findById(id)
        if (optionalClient.isPresent) {
            return optionalClient.get()
        } else {
            throw RuntimeException("Item not found")
        }
    }
}

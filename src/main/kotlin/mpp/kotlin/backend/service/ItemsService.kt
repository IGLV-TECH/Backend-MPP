package mpp.kotlin.backend.service

import domain.CategoryType
import domain.Item
import mpp.kotlin.backend.repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemsService(
    val itemRepository: ItemRepository
) {
    fun findAllByCategory(categoryType: CategoryType): Map<Int, String> {
        var elements = mutableMapOf<Int, String>()
        for (item: Item in itemRepository.findAllByCategory(categoryType))
            elements[item.getId()]=item.getElement()
        return elements
    }

    fun findAll(): MutableIterable<Item> {
        return itemRepository.findAll()
    }
}

package mpp.kotlin.backend.controller

import domain.Item
import mpp.kotlin.backend.repository.ItemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @GetMapping("/all")
    fun listAll(): List<Item> {
        return itemRepository.findAll()
    }
}
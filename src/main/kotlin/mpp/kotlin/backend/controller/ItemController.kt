package mpp.kotlin.backend.controller

import domain.CategoryType
import domain.Item
import mpp.kotlin.backend.service.ItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController {

    @Autowired
    private lateinit var itemsService: ItemsService

    @GetMapping()
    fun listAll(): MutableIterable<Item> {
        return itemsService.findAll()
    }

    @GetMapping("/findAllByCategory")
    fun findAllByCategory(@RequestParam categoryType: CategoryType): ArrayList<Map<String, String>> {
        return itemsService.findAllByCategory(categoryType)
    }
}
package mpp.kotlin.backend.controller

import domain.CategoryType
import domain.Item
import mpp.kotlin.backend.service.ItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/items")
class ItemController {

    @Autowired
    private lateinit var itemsService: ItemsService

    @GetMapping("/all")
    fun listAll(): MutableIterable<Item> {
        return itemsService.findAll()
    }

    @GetMapping("/findAllByCategory")
    fun findAllByCategory(@RequestParam categoryType: CategoryType): ArrayList<Map<String, String>> {
        return itemsService.findAllByCategory(categoryType)
    }
}
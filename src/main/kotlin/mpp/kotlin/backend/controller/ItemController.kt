package mpp.kotlin.backend.controller

import domain.CategoryType
import domain.Item
import mpp.kotlin.backend.service.ItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/items")
class ItemController {

    @Autowired
    private lateinit var itemsService: ItemsService

    @GetMapping("/all")
    fun listAll(): MutableIterable<Item> {
        return itemsService.findAll()
    }

    @GetMapping("/findAllByCategory")
    fun findAllByCategory(@RequestParam categoryType: CategoryType): Map<Int, String> {
        //val categoryType = request["categoryType"]
        return itemsService.findAllByCategory(categoryType)
    }
}
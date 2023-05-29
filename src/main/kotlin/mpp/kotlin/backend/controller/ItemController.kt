package mpp.kotlin.backend.controller

import TokenProvider
import UnauthorizedException
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

    private val tokenProvider: TokenProvider = TokenProvider()

    @GetMapping("/findAllByCategory")
    fun findAllByCategory(
        @RequestParam categoryType: CategoryType, @RequestHeader("Authorization") token: String
    ): ArrayList<Map<String, String>> {
        println(tokenProvider.getRoleFromToken(token))
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "employee") return itemsService.findAllByCategory(categoryType)
        else throw UnauthorizedException("Invalid token")
    }
}
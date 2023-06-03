package mpp.kotlin.backend.controller

import UnauthorizedException
import TokenProvider
import domain.CategoryType
import mpp.kotlin.backend.service.ItemsService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/items")
class ItemController {

    @Autowired
    private lateinit var itemsService: ItemsService

    private val tokenProvider: TokenProvider = TokenProvider

    private val logger = KotlinLogging.logger {}

    @GetMapping("/findAllByCategory")
    fun findAllByCategory(
        @RequestParam categoryType: CategoryType, @RequestHeader("Authorization") token: String
    ): ArrayList<Map<String, String>> {
        logger.info { "Finding all items by category: $categoryType" }
        println(tokenProvider.getRoleFromToken(token))
        if (tokenProvider.validateToken(token) && tokenProvider.getRoleFromToken(token) == "employee") {
            return itemsService.findAllByCategory(categoryType)
        } else {
            logger.error { "Unauthorized access to find all items by category: $categoryType" }
            throw UnauthorizedException("Invalid token")
        }
    }
}

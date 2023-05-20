package mpp.kotlin.backend.repository

import domain.CategoryType
import domain.Item
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository: CrudRepository<Item, Int> {
    fun findAllByCategory(category: CategoryType): List<Item>
}
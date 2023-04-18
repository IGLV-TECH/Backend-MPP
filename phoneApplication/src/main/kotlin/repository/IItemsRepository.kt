package repository

import domain.Item

interface IItemsRepository {
    fun findByID(id: Int): Item?
    fun findAll(): List <Item>?
    fun save(item: Item)
    fun update(item: Item)
}
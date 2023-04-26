package domain

import jakarta.persistence.*

@Entity
@Table(name = "Items")
class Item(
    @Column(name = "element")
    private var element: String,
    @Column(name = "price")
    private var price: Float,
    @Column(name = "category")
    private var category: CategoryType
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    fun getId(): Int {
        return this.id!!
    }

    fun getCategory(): CategoryType {
        return this.category
    }

    fun getElement(): String {
        return this.element
    }

    fun getPrice(): Float {
        return this.price
    }
}
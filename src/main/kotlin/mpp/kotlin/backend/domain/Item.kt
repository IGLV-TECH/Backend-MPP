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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Item

        if (element != other.element) return false
        if (price != other.price) return false
        if (category != other.category) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = element.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }

    override fun toString(): String {
        return "Item(element='$element', price=$price, category=$category, id=$id)"
    }
}
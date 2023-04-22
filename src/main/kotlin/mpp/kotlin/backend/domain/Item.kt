package domain

import jakarta.persistence.*

@Entity
@Table(name = "Items")
class Item(
    @Column(name = "element")
    private var element: ElementType,
    @Column(name = "price")
    private var price: Float,
    @Column(name = "category")
    private var category: CategoryType,
    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER, mappedBy = "items")
    private var invoices: MutableList<Invoice> = mutableListOf()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    fun getId(): Int? {
        return this.id
    }

    fun getCategory(): CategoryType {
        return this.category
    }

    fun getElement(): ElementType {
        return this.element
    }

    fun getPrice(): Float {
        return this.price
    }
}
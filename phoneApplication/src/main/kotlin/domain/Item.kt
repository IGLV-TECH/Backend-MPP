package domain
import javax.persistence.*;

@Entity
@Table(name = "items")
class Item(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private var id: Int = 0
    @Column(name = "element")
    private var element: String = ""

    @Column(name = "price")
    private var price: Double = 0.0

    @Column(name = "category")
    private var category: String = ""

    constructor(id: Int, element: String, price: Double, category: String): this(){
        this.id = id
        this.element = element
        this.price = price
        this.category = category
    }

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getElement(): String {
        return element
    }

    fun setElement(id: String) {
        this.element = element
    }

    fun getPrice(): Double {
        return price
    }

    fun setPrice(price: Double) {
        this.price = price
    }

    fun getCategory(): String {
        return element
    }

    fun setCategory(id: String) {
        this.element = element
    }

    @Override
    fun equals(item: Item): Boolean{
        return item.id == this.id;
    }

    override fun hashCode(): Int {
        var result = element.hashCode()
        result = 31 * result + id
        result = 31 * result + category.hashCode()
        return result
    }

}
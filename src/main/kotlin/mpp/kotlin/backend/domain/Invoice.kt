package domain

import java.util.*
import jakarta.persistence.*

@Entity
@Table(name = "Invoices")
class Invoice(
    @Column(name = "category")
    private var category: CategoryType,
    @Column(name = "payment")
    private var payment: Float,
    @Column(name = "penalty")
    private var penalty: Float,
    @Column(name = "date_of_issue")
    private var date: Date,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", referencedColumnName = "ID")
    private var client: Client,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee", referencedColumnName = "ID")
    private var employee: Employee,
    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(name = "Contents",
        joinColumns = [JoinColumn(name = "id_invoice", referencedColumnName = "ID")],
        inverseJoinColumns = [JoinColumn(name = "id_item", referencedColumnName = "ID")])
    private var items: MutableList<Item> = mutableListOf()
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

    fun getPayment(): Float {
        return this.payment
    }

    fun getPenalty(): Float {
        return this.penalty
    }

    fun getDate(): Date {
        return this.date
    }

    fun getClient(): Client {
        return this.client
    }

    fun getEmployee(): Employee {
        return this.employee
    }

    fun getItems(): MutableList<Item> {
        return this.items
    }
}
package domain

import java.util.*
import jakarta.persistence.*
import java.time.LocalDate

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
    private var date: LocalDate,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", referencedColumnName = "ID")
    private var client: Client,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "id_employee", referencedColumnName = "ID")
    private var employee: Employee,
    @OneToMany(mappedBy = "invoice", cascade = [CascadeType.ALL])
    @AssociationOverrides(
        AssociationOverride(name = "id.invoice", joinColumns = [JoinColumn(name = "id")]),
        AssociationOverride(name = "id.id_invoice", joinColumns = [JoinColumn(name = "id_invoice")])
    )
    var items: MutableList<Content> = mutableListOf()
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

    fun getPayment(): Float {
        return this.payment
    }

    fun getPenalty(): Float {
        return this.penalty
    }

    fun getDate(): LocalDate {
        return this.date
    }

    fun getClient(): Client {
        return this.client
    }

    fun getEmployee(): Employee {
        return this.employee
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Invoice

        if (category != other.category) return false
        if (payment != other.payment) return false
        if (penalty != other.penalty) return false
        if (date != other.date) return false
        if (client != other.client) return false
        if (employee != other.employee) return false
        if (items != other.items) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = category.hashCode()
        result = 31 * result + payment.hashCode()
        result = 31 * result + penalty.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + client.hashCode()
        result = 31 * result + employee.hashCode()
        result = 31 * result + items.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }

    override fun toString(): String {
        return "Invoice(category=$category, payment=$payment, penalty=$penalty, date=$date, client=$client, employee=$employee, items=$items, id=$id)"
    }
}
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

    fun getDate(): Date {
        return this.date
    }

    fun getClient(): Client {
        return this.client
    }

    fun getEmployee(): Employee {
        return this.employee
    }
}
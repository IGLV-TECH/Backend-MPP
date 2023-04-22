package domain

import jakarta.persistence.*

@Entity
@Table(name = "Clients")
class Client(
    @Column(name = "last_name") private var lastName: String,
    @Column(name = "first_name") private var firstName: String,
    @Column(name = "phone") private var phoneNumber: String,
    @Column(name = "email") private var email: String,
    @Column(name = "passwordC") private var password: String,
    @Column(name = "balance") private var balance: Float,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER) @JoinColumn(
        name = "id_address", referencedColumnName = "ID"
    ) private var address: Address
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Int? = null

    fun getId(): Int? {
        return this.id
    }

    fun getLastName(): String {
        return this.lastName
    }

    fun getFirstName(): String {
        return this.firstName
    }

    fun getPhoneNumber(): String {
        return this.phoneNumber
    }

    fun getEmail(): String {
        return this.email
    }

    fun getPassword(): String {
        return this.password
    }

    fun getBalance(): Float {
        return this.balance
    }

    fun getAddress(): Address {
        return this.address
    }
}
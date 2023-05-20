package domain

import jakarta.persistence.*

@Entity
@Table(name = "Employees")
class Employee(
    @Column(name = "last_name")
    private var lastName: String,
    @Column(name = "first_name")
    private var firstName: String,
    @Column(name = "phone")
    private var phoneNumber: String,
    @Column(name = "email")
    private var email: String,
    @Column(name = "passwordE")
    private var password: String,
    @OneToOne(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinColumn(name = "id_address", referencedColumnName = "ID")
    private var address: Address
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    fun getId(): Int {
        return this.id!!
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

    fun getAddress(): Address {
        return this.address
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (lastName != other.lastName) return false
        if (firstName != other.firstName) return false
        if (phoneNumber != other.phoneNumber) return false
        if (email != other.email) return false
        if (password != other.password) return false
        if (address != other.address) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + phoneNumber.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }

    override fun toString(): String {
        return "Employee(lastName='$lastName', firstName='$firstName', phoneNumber='$phoneNumber', email='$email', password='$password', address=$address, id=$id)"
    }
}
package domain

import jakarta.persistence.*

@Entity
@Table(name = "Addresses")
class Address(
    @Column(name = "county") private var county: String,
    @Column(name = "city") private var city: String,
    @Column(name = "street") private var street: String,
    @Column(name = "number") private var number: Int,
    @Column(name = "ZIPCode") private var ZIPCode: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int? = null

    fun getId(): Int {
        return this.id!!
    }

    fun getCounty(): String {
        return this.county
    }

    fun getCity(): String {
        return this.city
    }

    fun getStreet(): String {
        return this.street
    }

    fun getNumber(): Int {
        return this.number
    }

    fun getZIPCode(): String {
        return this.ZIPCode
    }
}
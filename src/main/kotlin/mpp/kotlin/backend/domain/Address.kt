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

    fun myEquals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (county != other.county) return false
        if (city != other.city) return false
        if (street != other.street) return false
        if (number != other.number) return false
        if (ZIPCode != other.ZIPCode) return false

        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Address

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = county.hashCode()
        result = 31 * result + city.hashCode()
        result = 31 * result + street.hashCode()
        result = 31 * result + number
        result = 31 * result + ZIPCode.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }

    override fun toString(): String {
        return "Address(id=$id, county='$county', city='$city', street='$street', number=$number, ZIPCode='$ZIPCode')"
    }
}
package domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.ManyToOne
import java.io.Serializable

@Embeddable
class ContentId(
    @Column(name = "id_invoice")
    private val idInvoice: Int,
    @Column(name = "id_item")
    private val idItem: Int
): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ContentId

        if (idInvoice != other.idInvoice) return false
        if (idItem != other.idItem) return false

        return true
    }

    override fun hashCode(): Int {
        var result = idInvoice
        result = 31 * result + idItem
        return result
    }
}

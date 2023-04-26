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
): Serializable {}

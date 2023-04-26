package domain

import jakarta.persistence.*

@Entity
@Table(name = "Contents")
class Content(
    @EmbeddedId
    private var id: ContentId,
    @ManyToOne
    @JoinColumn(name = "id_invoice")
    @MapsId("idInvoice")
    private var invoice: Invoice,
    @ManyToOne
    @JoinColumn(name = "id_item")
    @MapsId("idItem")
    private var item: Item,
    @Column(name = "quantity")
    private var quantity: Int
) {}
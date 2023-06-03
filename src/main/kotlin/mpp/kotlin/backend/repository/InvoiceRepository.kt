package mpp.kotlin.backend.repository

import domain.Invoice
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository: CrudRepository<Invoice, Int> {}
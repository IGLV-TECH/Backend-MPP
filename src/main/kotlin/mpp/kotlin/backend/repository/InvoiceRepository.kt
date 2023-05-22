package mpp.kotlin.backend.repository

import domain.Employee
import domain.Invoice
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InvoiceRepository: CrudRepository<Invoice, Int> {
//    @Query("SELECT i FROM Invoice i ORDER BY i.id ASC")
//    fun findAllInvoices(pageable: Pageable): List<Invoice>
}
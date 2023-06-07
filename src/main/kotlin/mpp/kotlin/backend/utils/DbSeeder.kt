package mpp.kotlin.backend.utils

import domain.*
import mpp.kotlin.backend.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.time.LocalDate
import java.util.*
import javax.xml.bind.DatatypeConverter

@Component
class DbSeeder(
    val addressRepository: AddressRepository,
    val adminRepository: AdminRepository,
    val clientRepository: ClientRepository,
    val employeeRepository: EmployeeRepository,
    val invoiceRepository: InvoiceRepository,
    val itemRepository: ItemRepository
) : CommandLineRunner {

    private fun encryptPassword(password: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val hashBytes = md.digest(password.toByteArray())
        return DatatypeConverter.printHexBinary(hashBytes).lowercase(Locale.getDefault())
    }

    override fun run(vararg args: String?) {

        this.employeeRepository.deleteAll()
        this.clientRepository.deleteAll()
        this.adminRepository.deleteAll()
        this.addressRepository.deleteAll()
        this.itemRepository.deleteAll()
        this.invoiceRepository.deleteAll()

        for (i in 1..10) {
            val address = Address("Test$i", "Test$i", "Test$i", i, "1234")
            this.addressRepository.save(address)

            val admin = Admin("Test$i", "Test$i", "07noidoi", "test$i@mpp.com", encryptPassword("admin$i"), address)
            this.adminRepository.save(admin)

            val client = Client(
                "Test$i", "Test$i", "07noidoi", "test$i@mpp.com", encryptPassword("client$i"), i.toFloat(), address
            )
            this.clientRepository.save(client)

            val employee =
                Employee("Test$i", "Test$i", "07noidoi", "test$i@mpp.com", encryptPassword("employee$i"), address)
            this.employeeRepository.save(employee)

            val item = Item("Item$i", i.toFloat(), CategoryType.PAPER_AND_CARDBOARD)
            this.itemRepository.save(item)

            val invoice = Invoice(
                CategoryType.PAPER_AND_CARDBOARD, (i * 10).toFloat(), i.toFloat(), LocalDate.now(), client, employee
            )
            val savedInvoice = this.invoiceRepository.save(invoice)
            savedInvoice.items.add(Content(ContentId(savedInvoice.getId(), item.getId()), savedInvoice, item, i))
            this.invoiceRepository.save(savedInvoice)
        }

        println(" -- Database has been initialized")
    }
}

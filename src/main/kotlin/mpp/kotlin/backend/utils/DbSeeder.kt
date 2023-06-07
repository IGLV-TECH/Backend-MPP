package mpp.kotlin.backend.utils

import domain.*
import mpp.kotlin.backend.repository.*
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.security.MessageDigest
import java.time.LocalDate
import java.util.*
import javax.xml.bind.DatatypeConverter
import kotlin.random.Random

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

        val random = Random
        val counties = listOf("County A", "County B", "County C", "County D", "County E")
        val cities = listOf("City X", "City Y", "City Z")
        val streets = listOf("Street P", "Street Q", "Street R", "Street S", "Street T")
        val zipCodes = listOf("12345", "54321", "67890")
        val lastNames = listOf("Smith", "Johnson", "Brown", "Davis", "Miller")
        val firstNames = listOf("John", "Emily", "Michael", "Olivia", "William")

        this.employeeRepository.deleteAll()
        this.clientRepository.deleteAll()
        this.adminRepository.deleteAll()
        this.addressRepository.deleteAll()
        this.itemRepository.deleteAll()
        this.invoiceRepository.deleteAll()

        for (i in 1..10) {
            val county = counties[random.nextInt(counties.size)]
            val city = cities[random.nextInt(cities.size)]
            val street = streets[random.nextInt(streets.size)]
            val zipCode = zipCodes[random.nextInt(zipCodes.size)]
            val lastName = lastNames[random.nextInt(lastNames.size)]
            val firstName = firstNames[random.nextInt(firstNames.size)]

            val address = Address(county, city, street, i, zipCode)
            addressRepository.save(address)

            val admin = Admin("$lastName$i", "$firstName$i", "+40758509793", "test$i@mpp.com", encryptPassword("admin$i"), address)
            adminRepository.save(admin)

            val client = Client("$lastName$i", "$firstName$i", "+40758509793", "test$i@mpp.com", encryptPassword("client$i"), i.toFloat(), address)
            clientRepository.save(client)

            val employee = Employee("$lastName$i", "$firstName$i", "+40758509793", "test$i@mpp.com", encryptPassword("employee$i"), address)
            employeeRepository.save(employee)

            val itemName = "Item $i"
            val itemPrice = i.toFloat()
            val categoryType = CategoryType.values().random()
            val item = Item(itemName, itemPrice, categoryType)
            itemRepository.save(item)

            val invoiceCategoryType = CategoryType.values().random()
            val totalAmount = (i * 10).toFloat()
            val quantity = i.toFloat()
            val invoiceDate = LocalDate.now()

            val invoice = Invoice(invoiceCategoryType, totalAmount, quantity, invoiceDate, client, employee)
            val savedInvoice = invoiceRepository.save(invoice)
            savedInvoice.items.add(Content(ContentId(savedInvoice.getId(), item.getId()), savedInvoice, item, i))
            invoiceRepository.save(savedInvoice)
        }

        println(" -- Database has been initialized")
    }
}

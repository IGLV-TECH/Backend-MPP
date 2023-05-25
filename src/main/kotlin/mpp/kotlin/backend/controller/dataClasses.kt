package mpp.kotlin.backend.controller

import domain.Address
import domain.CategoryType
import jakarta.persistence.Column

data class InvoiceRequest(
    val idClient: Int,
    val idEmployee: Int,
    val categoryType: CategoryType,
    val penaltyPoints: Int,
    val listItems: List<Map<String, Int>>
)

data class ClientRequest(
    val lastName: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val balance: Float,
    val address: Address
)

data class EmployeeRequest(
    val lastName: String,
    val firstName: String,
    val phoneNumber: String,
    val email: String,
    val password: String,
    val address: Address
)
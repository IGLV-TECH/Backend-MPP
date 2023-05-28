package mpp.kotlin.backend.controller

import domain.*

data class InvoiceResponse(
    val id: Int,
    val categoryType: CategoryType,
    val payment: Float,
    val penalty: Float,
    val date: String,
    val client: Client,
    val employee: Employee,
    val listItems: List<Content>
)

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
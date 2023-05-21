package mpp.kotlin.backend.payments

import domain.Client
import mpp.kotlin.backend.repository.EmployeeRepository
import mpp.kotlin.backend.service.ClientService
import org.springframework.stereotype.Service

@Service
class PaymentsService(
    private val clientService: ClientService
)  {

    fun makePayment(client: Client, amount: Float){
        println("I will make the transfer of " + amount + " to the client " + client.getId() )
    }

}
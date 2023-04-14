package domain

import java.util.Date

class Invoice(category: String, payment: Float, data: Date, client: Client) {

    var category: String = category
        get(){
            return field
        }
        set(value){
            field = value
        }
    var payment: Float = payment
        get(){
            return field
        }
        set(value){
            field = value
        }
    var data: Date = data
        get(){
            return field
        }
        set(value){
            field = value
        }
    var client: Client = client
        get(){
            return field
        }
        set(value){
            field = value
        }

    fun setClientBalance(payment: Float){
        this.client.balance = this.client.balance + this.payment
    }
}
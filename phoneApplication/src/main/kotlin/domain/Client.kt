package domain

class Client(lastName: String, firstName: String, phoneNumber: String, email: String, balance: Float, invoiceList: List<Invoice>) {
    var lastName: String = lastName
        get(){
            return field
        }
        set(value){
            field = value
        }
    var firstName: String = firstName
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var phoneNumber: String = phoneNumber
        get() {
            return field
        }
        set(value) {
            field = value
        }

    var email: String = email
        get(){
            return field
        }
        set(value){
            field = value
        }

    var balance: Float = balance
        get(){
            return field
        }
        set(value){
            field = value
        }

    var invoiceList: List<Invoice> = invoiceList
        get(){
            return field
        }
        set(value){
            field = value
        }

    fun viewInvoice(invoice: Invoice){
        if (invoice in invoiceList)
            println("${invoice.category}, ${invoice.payment}, ${invoice.data}")
    }

    override fun toString(): String {
        return "Client(lastName='$lastName', firstName='$firstName', phoneNumber='$phoneNumber', email='$email', balance=$balance, invoiceList=$invoiceList)"
    }


}
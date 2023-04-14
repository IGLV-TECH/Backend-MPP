package domain

class Address{
    var state: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var city: String
        get(){
            return field
        }
        set(value){
            field = value
        }
    var street: String
        get(){
            return field
        }
        set(value){
            field = value
        }
    var number: Int
        get(){
            return field
        }
        set(value){
            field = value
        }
    var ZIPCode: String

    constructor(state: String, city: String, street: String, number: Int, ZIPCode: String) {
        this.state = state
        this.city = city
        this.street = street
        this.number = number
        this.ZIPCode = ZIPCode
    }


}
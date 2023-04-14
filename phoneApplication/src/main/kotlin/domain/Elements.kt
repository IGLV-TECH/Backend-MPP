package domain

class Elements(categoryType: CategoryType, elementType: ElementType, quantity: Int, penalty: Int, price: Float) {
    var categoryType: CategoryType =  categoryType
        get(){
            return field
        }
        set(value){
            field = value
        }
    var elementType: ElementType = elementType
        get(){
            return field
        }
        set(value){
            field = value
        }
    var quantity: Int = quantity
        get() {
            return field
        }
        set(value){
            field = value
        }
    var penalty: Int = penalty
        get(){
            return field
        }
        set(value){
            field = value
        }
    var price: Float = price
        get(){
            return field
        }
        set(value){
            field = value
        }
}
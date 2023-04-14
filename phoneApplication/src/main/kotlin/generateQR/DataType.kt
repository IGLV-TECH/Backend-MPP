package generateQR

import domain.CategoryType
import domain.Client
import domain.ElementType

class DataType(client: Client, category: CategoryType, elements: List<Pair<ElementType, Int>>) {
    var client: Client = client
        get(){
            return field
        }
        set(value){
            field = value
        }
    var category: CategoryType = category
        get(){
            return field
        }
        set(value){
            field = value
        }
    var elements: List<Pair<ElementType, Int>>  = elements
        get(){
            return field
        }
        set(value){
            field = value
        }

    override fun toString(): String {
        return "DataType(client=$client, category=$category, elements=$elements)"
    }


}
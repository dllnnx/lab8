package labs.shared.dto

import labs.shared.objects.Person
import java.io.Serializable
import java.util.*

class Response (status: ResponseStatus): Serializable {
    var message = ""
    lateinit var collection: LinkedList<Person?>

    constructor(status: ResponseStatus, message: String) : this(status) {
        this.message = message
    }

    constructor(status: ResponseStatus, message: String, collection: LinkedList<Person?>) : this(status, message){
        this.collection = collection
    }

}
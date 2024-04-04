package labs.dto

import labs.objects.Person
import java.io.Serializable
import java.util.LinkedList

class Response(var status: ResponseStatus) : Serializable {
    var message = ""
    var collection: LinkedList<Person?>? = null

    constructor(status: ResponseStatus, message: String) : this(status) {
        this.message = message
    }

    constructor(status: ResponseStatus, message: String, collection: LinkedList<Person?>) : this(status, message) {
        this.collection = collection
    }
}

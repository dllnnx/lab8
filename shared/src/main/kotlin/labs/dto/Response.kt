package labs.dto

import labs.objects.Person
import java.io.Serializable
import java.util.LinkedList
import java.util.Objects

class Response(var status: ResponseStatus) : Serializable {
    var message = ""
    var collection: LinkedList<Person>? = null

    constructor(status: ResponseStatus, message: String) : this(status) {
        this.message = message
    }

    constructor(status: ResponseStatus, message: String, collection: LinkedList<Person>) : this(status, message) {
        this.collection = collection
    }

    override fun toString(): String {
        return if (Objects.isNull(collection)) {
            "Response (Status: ${this.status}, message = ${this.message})"
        } else {
            "Response (Status: ${this.status}, message = ${this.message}, collection = ${this.collection})"
        }
    }
}

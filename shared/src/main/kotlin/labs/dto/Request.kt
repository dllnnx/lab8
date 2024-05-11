package labs.dto

import labs.objects.Person
import java.io.Serializable

class Request(val commandName: String, val user: User) : Serializable {
    var args = ""
    var person: Person? = null

    constructor(commandName: String, args: String, user: User) : this(commandName, user) {
        this.args = args
    }

    constructor(commandName: String, args: String, person: Person, user: User) : this(commandName, user) {
        this.args = args
        this.person = person
    }

    fun isEmpty(): Boolean {
        return commandName.isEmpty() && args.isEmpty() && person == null
    }
}

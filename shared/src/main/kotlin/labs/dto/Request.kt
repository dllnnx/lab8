package labs.dto

import labs.objects.Person
import java.io.Serializable

class Request(val commandName: String) : Serializable {
    var user = User("", "")
    var args = ""
    var person: Person? = null

    constructor(commandName: String, user: User): this(commandName) {
        this.user = user
    }

    constructor(commandName: String, args: String, user: User) : this(commandName) {
        this.args = args
        this.user = user
    }

    constructor(commandName: String, args: String, person: Person, user: User) : this(commandName) {
        this.args = args
        this.person = person
        this.user = user
    }

    fun isEmpty(): Boolean {
        return commandName.isEmpty() && args.isEmpty() && person == null
    }
}

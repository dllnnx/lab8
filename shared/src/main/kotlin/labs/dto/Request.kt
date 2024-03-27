package labs.shared.dto

import labs.shared.objects.Person
import java.io.Serializable

class Request (val commandName: String) : Serializable {
    var args = ""
    var person: Person? = null

    constructor(commandName: String, args: String): this(commandName){
        this.args = args
    }

    constructor(commandName: String, person: Person): this(commandName){
        this.person = person
    }

    fun isEmpty(): Boolean{
        return commandName.isEmpty() && args.isEmpty() && person == null
    }
}
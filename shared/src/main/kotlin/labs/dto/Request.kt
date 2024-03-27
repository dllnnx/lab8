package labs.dto

import labs.objects.Person
import java.io.Serializable

class Request (val commandName: String) : Serializable {
    var args = ""
    var person: Person? = null

    constructor(commandName: String, args: String): this(commandName){
        this.args = args
    }

    constructor(commandName: String, args: String, person: Person): this(commandName, ){
        this.args = args
        this.person = person
    }

    fun isEmpty(): Boolean{
        return commandName.isEmpty() && args.isEmpty() && person == null
    }
}
package labs.utility

import labs.commands.Command
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus

class CommandManager {

    var commands = HashMap<String, Command>()
    var commandHistory = ArrayList<String>()

    fun addCommands(commands: Collection<Command>) {
        this.commands.putAll(commands.associateBy { it.name })
    }

    fun execute(request: Request) : Response {
        val command: Command = commands[request.commandName]
            ?: return Response(ResponseStatus.ERROR, "Такой команды нет в списке!((")
        val response: Response = command.execute(request)
        return response
    }

    fun addToHistory(command: String) {
        commandHistory.add(command)
    }
}

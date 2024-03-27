package labs.server.utility

import labs.server.commands.Command
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus

class CommandManager {

    var commands = HashMap<String, labs.server.commands.Command>()
    var commandHistory = ArrayList<String>()

    fun addCommands(commands: Collection<labs.server.commands.Command>) {
        this.commands.putAll(commands.associateBy { it.name })
    }

    fun execute(request: Request) : Response {
        val command: labs.server.commands.Command = commands[request.commandName]
            ?: return Response(ResponseStatus.ERROR, "Такой команды нет в списке!((")
        val response: Response = command.execute(request)
        return response
    }

    fun addToHistory(command: String) {
        commandHistory.add(command)
    }
}

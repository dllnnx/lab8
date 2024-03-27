package labs.utility

import server.utility.CommandManager
import labs.dto.Request
import labs.dto.Response

class RequestHandler (private val commandManager: CommandManager) {
    fun handle(request: Request) : Response {
        commandManager.addToHistory(request.commandName)
        return commandManager.execute(request)
    }
}
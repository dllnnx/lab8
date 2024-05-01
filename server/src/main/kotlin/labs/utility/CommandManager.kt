package labs.utility

import labs.commands.Command
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.dto.User
import kotlin.math.max

class CommandManager {
    var commands = HashMap<String, Command>()
    var commandHistory = ArrayList<Pair<String, User>>()
    private val maxCommandHistorySize = 10

    fun addCommands(commands: Collection<Command>) {
        this.commands.putAll(commands.associateBy { it.name })
    }

    suspend fun execute(request: Request): Response {
        val command: Command =
            commands[request.commandName]
                ?: return Response(ResponseStatus.ERROR, "Такой команды нет в списке!((")
        if (request.commandName != "login" && request.commandName != "register") {
            addToHistory(request.commandName, request.user!!)
        }
        val response: Response = command.execute(request)
        return response
    }

    private fun addToHistory(
        command: String,
        user: User,
    ) {
        commandHistory.add(Pair(command, user))
    }

    fun removeLastCommand() {
        commandHistory.removeLast()
    }

    fun showUserCommands(): String {
        return commands
            .filter { it.key != "login" && it.key != "register" }
            .values
            .joinToString("\n")
    }

    fun showHistory(user: User): String {
        val userHistory =
            commandHistory
                .filter { it.second == user }
                .map { it.first }
        val resp =
            userHistory
                .subList(max(0, userHistory.size - maxCommandHistorySize), userHistory.size)
                .joinToString("\n")
        return resp
    }
}

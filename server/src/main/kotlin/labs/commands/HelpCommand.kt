package labs.server.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.server.utility.CommandManager
import labs.utility.Console

/**
 * Команда help. Выводит справку по доступным командам.
 * @author dllnnx
 */
class HelpCommand(private val console: Console, private val commandManager: CommandManager) :
    labs.server.commands.Command("help", ": вывести справку по доступным командам.") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        return Response(ResponseStatus.OK,
            commandManager.commands.forEach { (commandName, command) -> command.toString() + "\n" }.toString())
    }
}

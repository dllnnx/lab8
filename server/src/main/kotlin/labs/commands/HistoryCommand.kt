package labs.server.commands

import labs.server.utility.CommandManager
import labs.utility.Console
import shared.utility.Console
import server.utility.CommandManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus
import kotlin.math.max

/**
 * Команда history. Выводит последние 10 команд (без их аргументов).
 * @author dllnnx
 */
class HistoryCommand(private val console: Console, private val commandManager: CommandManager) :
    labs.server.commands.Command("history", ":  вывести последние 10 команд (без их аргументов).") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        val history: List<String> = commandManager.commandHistory
        val resp = ""
        if (history.isNotEmpty()) {
            for (command in history.subList(max(0, (history.size - 10)), history.size)) {
                resp.plus(command + "\n")
            }
            return Response(ResponseStatus.OK, resp)
        } else {
            return Response(ResponseStatus.WARNING, "Вы еще не ввели ни одной команды! :(((")
        }
    }
}

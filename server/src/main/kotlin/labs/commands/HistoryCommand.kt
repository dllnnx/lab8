package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CommandManager
import kotlin.math.max

/**
 * Команда history. Выводит последние 10 команд (без их аргументов).
 * @author dllnnx
 */
class HistoryCommand(private val commandManager: CommandManager) :
    Command("history", ":  вывести последние 10 команд (без их аргументов).") {
    override fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        val history: List<String> = commandManager.commandHistory
        if (history.isNotEmpty()) {
            val resp = history.subList(max(0, (history.size - 10)), history.size).joinToString("\n")
            return Response(ResponseStatus.OK, resp)
        } else {
            return Response(ResponseStatus.WARNING, "Вы еще не ввели ни одной команды! :(((")
        }
    }
}

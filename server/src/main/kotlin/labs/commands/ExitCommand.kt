package labs.server.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.Console

/**
 * Команда exit. Завершает программу без сохранения в файл.
 * @author dllnnx
 */
class ExitCommand(private val console: Console) : labs.server.commands.Command("exit", ": завершить программу (без сохранения в файл).") {

    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        return Response(ResponseStatus.EXIT)
    }
}

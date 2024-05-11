package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus

/**
 * Команда exit. Завершает программу без сохранения в файл.
 * @author dllnnx
 */
class ExitCommand() : Command("exit", ": завершить программу (без сохранения в файл).") {
    override suspend fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        return Response(ResponseStatus.EXIT)
    }
}

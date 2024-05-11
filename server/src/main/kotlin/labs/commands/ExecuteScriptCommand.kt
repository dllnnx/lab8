package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus

/**
 * Команда execute_script. Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 * @author dllnnx
 */
class ExecuteScriptCommand() :
    Command("execute_script", " file_name: считать и исполнить скрипт из указанного файла.") {
    override suspend fun execute(request: Request): Response {
        if (request.args.isBlank()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды требуется аргумент!")
        }

        if (request.args.split(" ").size != 1) {
            return Response(
                ResponseStatus.WRONG_ARGUMENTS,
                "Неверное количество аргументов! " +
                    "Введено: " + request.args.split(" ").size + ", ожидалось: 1.",
            )
        }

        return Response(ResponseStatus.EXECUTE_SCRIPT, request.args)
    }
}

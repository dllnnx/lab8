package labs.commands

import labs.utility.CommandManager
import labs.utility.Console

/**
 * Команда execute_script. Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 * @author dllnnx
 */
class ExecuteScriptCommand(
    private val console: Console,
    private val commandManager: CommandManager,
) {
//    override fun execute(request: Request): Response {
//        try {
//            if (request.args.trim().split(" ").size != 1) {
//                return Response(
//                    ResponseStatus.WRONG_ARGUMENTS,
//                    "Неверное количество аргументов! " +
//                        "Ожидалось: 1, введено: " + request.args.trim().split(" ").size + ".",
//                )
//            }
//
//            val filePath = request.args.trim().split(" ")[0]
//            Console.fileMode = true
//            ScriptManager.addFile(filePath)
//
//            var line = scriptManager.nextLine()
//            while (line!!.isNotBlank()) {
//                val command: List<String> = line.split(" ")
// //                commandManager.addToHistory((command[0]))
// //                if (command[0].isBlank()) return
//                if ((command[0] == "execute_script")) {
//                    if (ScriptManager.isRecursive(command[1])) {
//                        console.printError(
//                            "Найдена рекурсия! Повторно вызывается файл " +
//                                File(command[1]).absolutePath,
//                        )
//                        line = scriptManager.nextLine()
//                        continue
//                    }
//                }
//
//                if (commandManager.commands[command[0]] != null) {
//                    console.println(
//                        ConsoleColor.setConsoleColor(
//                            "Выполнение команды " + command[0] + "...",
//                            ConsoleColor.CYAN,
//                        ),
//                    )
// //                    commandManager.execute((command[0]), command.slice(1..<command.size))
//                } else {
//                    console.printError("Вызываемой в скрипте команды не существует!")
//                }
//                if ((command[0] == "execute_script")) {
//                    Console.fileMode = true
//                }
//                line = scriptManager.nextLine()
//            }
//            ScriptManager.removeFile()
//        } catch (e: FileNotFoundException) {
//            console.printError("Такой файл не найден((")
//        }
//        Console.fileMode = false
//        return Response(ResponseStatus.OK)
//    }
}

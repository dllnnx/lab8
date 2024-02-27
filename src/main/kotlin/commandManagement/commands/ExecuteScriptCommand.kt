package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CommandManager
import managers.ScriptManager
import java.io.File
import java.io.FileNotFoundException
import java.util.*

/**
 * Команда execute_script. Считывает и исполняет скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
 * @author dllnnx
 */
class ExecuteScriptCommand(
    private val console: Console,
    private val commandManager: CommandManager,
    private val scriptManager: ScriptManager
) :
    Command("execute_script",
        " file_name: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме."
    ) {
    /**
     * Выполнить команду
     * @param args аргумент команды
     */
    override fun execute(args: List<String?>) {
        try {
            if (args.size != 1) {
                console.printError("Неверное количество аргументов! Ожидалось: 1, введено: "+ args.size + ".")
                return
            }

            val filePath = args[0]
            Console.fileMode = true
            ScriptManager.addFile(filePath)

            var line = scriptManager.nextLine()
            while (line!!.isNotBlank()) {
                val command: List<String> = line.split(" ")
                commandManager.addToHistory((command[0]))
                if (command[0].isBlank()) return
                if ((command[0] == "execute_script")) {
                    if (ScriptManager.isRecursive(command[1])) {
                        console.printError("Найдена рекурсия! Повторно вызывается файл "
                                + File(command[1]).absolutePath)
                        line = scriptManager.nextLine()
                        continue
                    }
                }

                if (commandManager.commands[command[0]] != null) {
                    console.println(ConsoleColor.setConsoleColor("Выполнение команды " + command[0] + "...",
                        ConsoleColor.CYAN))
                    commandManager.execute((command[0]), command.slice(1..<command.size))
                } else {
                    console.printError("Вызываемой в скрипте команды не существует!")
                }
                if ((command[0] == "execute_script")) {
                    Console.fileMode = true
                }
                line = scriptManager.nextLine()
            }
            ScriptManager.removeFile()
        } catch (e: FileNotFoundException) {
            console.printError("Такой файл не найден((")
        }
        Console.fileMode = false
    }
}

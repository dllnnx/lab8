package labs.client.utility

import shared.utility.Printable
import server.utility.CommandManager
import server.utility.FileManager
import server.utility.ScriptManager
import shared.dto.Request
import shared.dto.Response
import shared.utility.Console
import shared.utility.ConsoleColor
import java.io.File
import java.io.FileNotFoundException
import java.util.*
import kotlin.system.exitProcess

/**
 * Класс для обработки запуска программы.
 * @author dllnnx
 */
class RuntimeManager constructor(var console: Printable, private var commandManager: CommandManager,
                                 private var fileManager: FileManager, var client: Client
) {

    /**
     * Запускает работу программы в интерактивном режиме (в стандартной консоли).
     */
    fun interactiveMode() {
        val userScanner: Scanner = ScannerManager.userScanner
        fileManager.fillCollection()
        console.println("Чтобы увидеть список допустимых команд, введите help")
        while (true) {
            try {
                console.print("$ ")
                val userCommand = userScanner.nextLine().trim()
                val command = userCommand.split(" ")
                if (command.isNotEmpty()){
                    launch(command)
                }
            } catch (e: NoSuchElementException) {
                exitProcess(0)
            }
        }
    }

    fun fileExecutionMode(args: String) {
        try {
            val filePath = args.trim()
            Console.fileMode = true
            ScriptManager.addFile(filePath)

            var line = ScriptManager.nextLine()
            while (line!!.isNotBlank()) {
                val command: List<String> = line.split(" ")
                commandManager.addToHistory((command[0]))
                if (command[0].isBlank()) return
                if (command[0] == "execute_script") {
                    if (ScriptManager.isRecursive(command[1])) {
                        console.printError("Найдена рекурсия! Повторно вызывается файл "
                                + File(command[1]).absolutePath)
                        line = ScriptManager.nextLine()
                        continue
                    }
                }

                if (commandManager.commands[command[0]] != null) {
                    console.println(
                        ConsoleColor.setConsoleColor("Выполнение команды " + command[0] + "...",
                            ConsoleColor.CYAN))
                    val response : Response = client.sendAndReceiveResponse(Request(command[0].trim(), command[1].trim()))
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

    /**
     * Запускает выполнение команды.
     * @param userCommand Введенная пользователем команда с аргументами
     */
    private fun launch(userCommand: List<String>) {
        if (userCommand[0].isBlank()) return
        val args = userCommand.slice(1..<userCommand.size)
        if (commandManager.commands[userCommand[0]] != null) {
            commandManager.addToHistory(userCommand[0])
            commandManager.execute(userCommand[0], args)
        } else {
            console.printError("Такой команды нет!( Попробуйте еще раз!)).")
        }
    }
}

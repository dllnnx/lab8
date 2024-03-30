package labs.utility

import labs.cli.forms.PersonForm
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import java.io.File
import java.io.FileNotFoundException
import java.util.Scanner
import kotlin.NoSuchElementException

/**
 * Класс для обработки запуска программы.
 * @author dllnnx
 */
class RuntimeManager(private var console: Printable, private var userScanner: Scanner, private var client: Client,
    private var scriptManager: ScriptManager) {
    /**
     * Запускает работу программы в интерактивном режиме (в стандартной консоли).
     */

    fun interactiveMode() {
        console.println("Чтобы увидеть список допустимых команд, введите help")
        while (true) {
            try {
                console.print("$ ")
                if (!userScanner.hasNext()) {
                    console.println(ConsoleColor.setConsoleColor("Программа завершена. До свидания!))", ConsoleColor.PURPLE))
                    return
                }
                val userCommand = (userScanner.nextLine().trim() + " ").split(" ", limit = 2)
                val response = client.sendAndReceiveResponse(Request(userCommand[0].trim(), userCommand[1].trim()))
                reactToResponse(response, userCommand)
            } catch (e: NoSuchElementException) {
                console.printError("Пользовательский ввод не обнаружен! :(")
            }
        }
    }

    private fun reactToResponse (response: Response, userCommand: List<String>) {
        when (response.status) {
            ResponseStatus.OK -> {
                if (response.collection == null) {
                    console.println(response.message)
                } else {
                    console.println(response.message)
                    for (person in response.collection!!) {
                        console.println(person.toString() + "\n")
                    }
                }
            }

            ResponseStatus.ERROR -> console.printError(response.message)
            ResponseStatus.WRONG_ARGUMENTS -> console.printError(response.message)
            ResponseStatus.WARNING -> console.println(ConsoleColor.setConsoleColor(response.message,
                ConsoleColor.YELLOW))

            ResponseStatus.OBJECT_REQUIRED -> {
                val person = PersonForm(console).build()
                if (!Validator().validatePerson(person)) {
                    console.printError("Поля не валидны! Объект не создан :((")
                }

                val newResponse =
                    client.sendAndReceiveResponse(
                        Request(
                            userCommand[0].trim(),
                            userCommand[1].trim(),
                            person,
                        ),
                    )

                if (newResponse.status == ResponseStatus.OK) {
                    console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.GREEN))
                } else if (newResponse.status == ResponseStatus.WARNING) {
                    console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.YELLOW))
                } else {
                    console.printError(newResponse.message)
                }
            }

            ResponseStatus.EXECUTE_SCRIPT -> scriptExecutionMode(response.message)

            ResponseStatus.EXIT -> {
                console.println(ConsoleColor.setConsoleColor("Программа завершена. До свидания!))",
                    ConsoleColor.PURPLE))
                return
            }
        }
    }

    private fun scriptExecutionMode(args: String) {
        try {
            val filePath = args.trim()
            Console.fileMode = true
            ScriptManager.addFile(filePath)

            var line = scriptManager.nextLine()
            while (line!!.isNotBlank()) {
                val command: List<String> = ("$line ").split(" ", limit = 2)
                if (command[0] == "execute_script") {
                    if (ScriptManager.isRecursive(command[1].trim())) {
                        console.printError("Найдена рекурсия! Повторно вызывается файл "
                            + File(command[1]).absolutePath)
                        line = scriptManager.nextLine()
                        continue
                    }
                }

                console.println(ConsoleColor.setConsoleColor("Выполнение команды " + command[0],
                    ConsoleColor.CYAN))
                val response = client.sendAndReceiveResponse(Request(command[0].trim(), command[1].trim()))
                if ((command[0] == "execute_script")) {
                    Console.fileMode = true
                }
                reactToResponse(response, command)

                line = scriptManager.nextLine()
            }
            ScriptManager.removeFile()
            console.println(ConsoleColor.setConsoleColor("Скрипт $args выполнен!", ConsoleColor.GREEN))
        } catch (e: FileNotFoundException) {
            console.printError("Такой файл не найден :((")
        }
        Console.fileMode = false
    }
}

package labs.utility

import labs.cli.forms.PersonForm
import labs.cli.forms.UserForm
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.dto.User
import java.io.File
import java.io.FileNotFoundException
import java.util.Objects
import java.util.Scanner
import kotlin.NoSuchElementException
import kotlin.system.exitProcess

/**
 * Класс для обработки запуска программы.
 * @author dllnnx
 */
class RuntimeManager(
    private var console: Printable,
    private var userScanner: Scanner,
    private var client: Client,
    private var scriptManager: ScriptManager,
) {
    /**
     * Запускает работу программы в интерактивном режиме (в стандартной консоли).
     */
    private lateinit var user: User

    fun interactiveMode() {
        var response: Response? = null
        var isLogin = true
        do {
            if (!Objects.isNull(response)) {
                if (isLogin) {
                    console.printError("Пользователь не найден, проверьте логин и пароль!")
                } else {
                    console.printError("Этот логин уже занят, попробуйте снова!")
                }
            }

            isLogin = UserForm(console).askIfLogin()
            user = UserForm(console).build()

            response =
                if (isLogin) {
                    client.sendAndReceiveResponse(Request("login", "", user))
                } else {
                    client.sendAndReceiveResponse(Request("register", "", user))
                }
        } while (response!!.status != ResponseStatus.OK)
        console.println(ConsoleColor.setConsoleColor("----------------------------------------------", ConsoleColor.GREEN))
        console.println(ConsoleColor.setConsoleColor("---------- Вход в аккаунт выполнен! ----------", ConsoleColor.GREEN))
        console.println(ConsoleColor.setConsoleColor("----------------------------------------------", ConsoleColor.GREEN))

        console.println("Чтобы увидеть список допустимых команд, введите help")
        while (true) {
            try {
                console.print("$ ")
                if (!userScanner.hasNextLine()) {
                    console.println(ConsoleColor.setConsoleColor("Программа завершена. До свидания!))", ConsoleColor.PURPLE))
                    return
                }
                val userCommand = (userScanner.nextLine().trim() + " ").split(" ", limit = 2)
                response = client.sendAndReceiveResponse(Request(userCommand[0].trim(), userCommand[1].trim(), user))
                reactToResponse(response, userCommand)
            } catch (e: NoSuchElementException) {
                console.printError("Пользовательский ввод не обнаружен! :(")
            }
        }
    }

    private fun reactToResponse(
        response: Response,
        userCommand: List<String>,
    ) {
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
            ResponseStatus.WARNING ->
                console.println(
                    ConsoleColor.setConsoleColor(
                        response.message,
                        ConsoleColor.YELLOW,
                    ),
                )

            ResponseStatus.OBJECT_REQUIRED -> {
                val person = PersonForm(console).build()
                if (!Validator().validatePerson(person)) {
                    console.printError("Поля не валидны! Объект не создан :((")
                    return
                }

                val newResponse =
                    client.sendAndReceiveResponse(
                        Request(
                            userCommand[0].trim(),
                            userCommand[1].trim(),
                            person,
                            user,
                        ),
                    )

                when (newResponse.status) {
                    ResponseStatus.OK -> {
                        console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.GREEN))
                    }
                    ResponseStatus.WARNING -> {
                        console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.YELLOW))
                    }
                    else -> {
                        console.printError(newResponse.message)
                    }
                }
            }

            ResponseStatus.EXECUTE_SCRIPT -> scriptExecutionMode(response.message)

            ResponseStatus.EXIT -> {
                console.println(
                    ConsoleColor.setConsoleColor(
                        "Программа завершена. До свидания!))",
                        ConsoleColor.PURPLE,
                    ),
                )
                exitProcess(0)
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
                        console.printError(
                            "Найдена рекурсия! Повторно вызывается файл " +
                                File(command[1]).absolutePath,
                        )
                        line = scriptManager.nextLine()
                        continue
                    }
                }

                console.println(
                    ConsoleColor.setConsoleColor(
                        "Выполнение команды " + command[0],
                        ConsoleColor.CYAN,
                    ),
                )
                val response = client.sendAndReceiveResponse(Request(command[0].trim(), command[1].trim(), user))
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

package labs.utility

import labs.cli.forms.PersonForm
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import java.util.*
import kotlin.NoSuchElementException

/**
 * Класс для обработки запуска программы.
 * @author dllnnx
 */
class RuntimeManager (var console: Printable, var userScanner: Scanner, var client: Client) {

    /**
     * Запускает работу программы в интерактивном режиме (в стандартной консоли).
     */

    fun interactiveMode(){
        console.println("Чтобы увидеть список допустимых команд, введите help")
        while (true){
            try {
                console.print("$ ")
                if (!userScanner.hasNext()){
                    console.println(ConsoleColor.setConsoleColor("Программа завершена. До свидания!))", ConsoleColor.PURPLE))
                    return
                }
                val userCommand = (userScanner.nextLine().trim() +" ").split(" ", limit = 2)
                val response = client.sendAndReceiveResponse(Request(userCommand[0].trim(), userCommand[1].trim()))
                printResponse(response)
                when(response.status){
                    ResponseStatus.OBJECT_REQUIRED -> {
                        val person = PersonForm(console).build()
                        if (!Validator().validatePerson(person)){
                            console.printError("Поля не валидны! Объект не создан :((")
                        }

                        val newResponse = client.sendAndReceiveResponse(Request(
                            userCommand[0].trim(), userCommand[1].trim(), person))

                        if (newResponse.status == ResponseStatus.OK)
                            console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.GREEN))
                        else if (newResponse.status == ResponseStatus.WARNING)
                            console.println(ConsoleColor.setConsoleColor(newResponse.message, ConsoleColor.YELLOW))
                        else
                            console.printError(newResponse.message)
                    }

//                    ResponseStatus.EXECUTE_SCRIPT -> TODO()

                    ResponseStatus.EXIT -> {
                        console.println(ConsoleColor.setConsoleColor("Программа завершена. До свидания!))",
                            ConsoleColor.PURPLE))
                        return
                    }
                    else -> {}
                }
            } catch (e: NoSuchElementException){
                console.printError("Пользовательский ввод не обнаружен! :(")
            }
        }
    }

    private fun printResponse(response: Response){
        when(response.status){
            ResponseStatus.OK -> {
                if (response.collection == null) console.println(response.message)
                else {
                    console.println(response.message)
                    for (person in response.collection!!){
                        console.println(person.toString() + "\n")
                    }
                }
            }
            ResponseStatus.ERROR -> console.printError(response.message)
            ResponseStatus.WRONG_ARGUMENTS -> console.printError(response.message)
            ResponseStatus.WARNING -> console.println(ConsoleColor.setConsoleColor(response.message, ConsoleColor.YELLOW))
            else -> {}
        }
    }


//    fun interactiveMode() {
//        val userScanner: Scanner = ScannerManager.userScanner
//        fileManager.fillCollection()
//        console.println("Чтобы увидеть список допустимых команд, введите help")
//        while (true) {
//            try {
//                console.print("$ ")
//                val userCommand = userScanner.nextLine().trim()
//                val command = userCommand.split(" ")
//                if (command.isNotEmpty()){
//                    launch(command)
//                }
//            } catch (e: NoSuchElementException) {
//                exitProcess(0)
//            }
//        }
//    }

//    fun fileExecutionMode(args: String) {
//        try {
//            val filePath = args.trim()
//            Console.fileMode = true
//            ScriptManager.addFile(filePath)
//
//            var line = ScriptManager.nextLine()
//            while (line!!.isNotBlank()) {
//                val command: List<String> = line.split(" ")
//                commandManager.addToHistory((command[0]))
//                if (command[0].isBlank()) return
//                if (command[0] == "execute_script") {
//                    if (ScriptManager.isRecursive(command[1])) {
//                        console.printError("Найдена рекурсия! Повторно вызывается файл "
//                                + File(command[1]).absolutePath)
//                        line = ScriptManager.nextLine()
//                        continue
//                    }
//                }
//
//                if (commandManager.commands[command[0]] != null) {
//                    console.println(
//                        ConsoleColor.setConsoleColor("Выполнение команды " + command[0] + "...",
//                            ConsoleColor.CYAN))
//                    val response : Response = client.sendAndReceiveResponse(Request(command[0].trim(), command[1].trim()))
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
//        }
//    }


//    private fun launch(userCommand: List<String>) {
//        if (userCommand[0].isBlank()) return
//        val args = userCommand.slice(1..<userCommand.size)
//        if (commandManager.commands[userCommand[0]] != null) {
//            commandManager.addToHistory(userCommand[0])
//            commandManager.execute(userCommand[0], args)
//        } else {
//            console.printError("Такой команды нет!( Попробуйте еще раз!)).")
//        }
//    }
}

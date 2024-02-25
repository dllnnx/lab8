package managers

import commandManagement.Printable
import java.util.*
import kotlin.system.exitProcess

/**
 * Класс для обработки запуска программы.
 * @author dllnnx
 */
class RuntimeManager constructor(var console: Printable, private var commandManager: CommandManager,
                                 private var fileManager: FileManager) {

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
                launch(userCommand.split(" "))
            } catch (e: NoSuchElementException) {
                console.printError("Конец ввода... До свидания!))")
                exitProcess(0)
            } catch (ignored: ArrayIndexOutOfBoundsException) {
            }
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

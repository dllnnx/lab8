package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import kotlin.system.exitProcess

/**
 * Команда exit. Завершает программу без сохранения в файл.
 * @author dllnnx
 */
class ExitCommand(private val console: Console) : Command("exit", ": завершить программу (без сохранения в файл).") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }
        console.println(ConsoleColor.setConsoleColor("Программа завершена. До встречи!)))", ConsoleColor.PURPLE))
        exitProcess(0)
    }
}

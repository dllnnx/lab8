package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CommandManager
import kotlin.math.max

/**
 * Команда history. Выводит последние 10 команд (без их аргументов).
 * @author dllnnx
 */
class HistoryCommand(private val console: Console, private val commandManager: CommandManager) :
    Command("history", ":  вывести последние 10 команд (без их аргументов).") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }

        val history: List<String> = commandManager.commandHistory
        if (history.isNotEmpty()) {
            for (command in history.subList(max(0, (history.size - 10)), history.size)) {
                console.println(command)
            }
        } else {
            console.println("Вы еще не ввели ни одной команды! :(((")
        }
    }
}

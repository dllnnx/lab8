package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CommandManager

/**
 * Команда help. Выводит справку по доступным командам.
 * @author dllnnx
 */
class HelpCommand(private val console: Console, private val commandManager: CommandManager) :
    Command("help", ": вывести справку по доступным командам.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }
        commandManager.commands.forEach { (commandName, command) -> console.println(command.toString()) }
    }
}

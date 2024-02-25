package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CollectionManager

/**
 * Команда clear. Очищает коллекцию
 * @author dllnnx
 */
class ClearCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("clear", ": очистить коллекцию.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }
        if (collectionManager.getCollectionSize() == 0) {
            console.printError("Коллекция уже пуста!")
            return
        }
        collectionManager.clearCollection()
        console.println(ConsoleColor.setConsoleColor("Коллекция успешно очищена!", ConsoleColor.GREEN))
    }
}

package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CollectionManager

/**
 * Команда shuffle. Перемешивает элементы коллекции в случайном порядке.
 * @author dllnnx
 */
class ShuffleCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("shuffle", ": перемешать элементы коллекции в случайном порядке.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }

        if (collectionManager.getCollectionSize() != 0) {
            collectionManager.shuffle()
            console.println(ConsoleColor.setConsoleColor("Коллекция успешно перемешана!", ConsoleColor.GREEN))
        } else {
            console.printError("Коллекция пуста!")
        }
    }
}

package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CollectionManager

/**
 * Команда remove_first. Удаляет первый элемент из коллекции.
 * @author dllnnx
 */
class RemoveFirstCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("remove_first", ": удалить первый элемент из коллекции.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isEmpty()) {
            if (collectionManager.getCollectionSize() != 0) {
                collectionManager.removeFirst()
                console.println(ConsoleColor.setConsoleColor("Первый элемент коллекции успешно удален!",
                    ConsoleColor.GREEN)
                )
            } else console.printError("Коллекция пуста!")
        } else console.printError("Для этой команды не требуются аргументы!")
    }
}

package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CollectionManager

/**
 * Команда info. Выводит в стандартный поток вывода информацию о коллекции.
 * @author dllnnx
 */
class InfoCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("info", ": вывести в стандартный поток вывода информацию о коллекции.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }
        console.println(
            """
                Информация о коллекции: 
                Тип коллекции: ${collectionManager.getCollectionType()}
                Количество элементов: ${collectionManager.getCollectionSize()}
                Дата инициализации: ${collectionManager.initializationTime}
                """.trimIndent()
        )
    }
}

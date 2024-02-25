package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CollectionManager

/**
 * Команда max_by_nationality. Выводит любой элемент из коллекции, значение поля nationality которого является максимальным.
 * @author dllnnx
 */
class MaxByNationalityCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command(
        "max_by_nationality",
        ": вывести любой объект из коллекции, значение поля nationality которого является максимальным."
    ) {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isNotEmpty()) {
            console.printError("Для этой команды не требуются аргументы!")
            return
        }

        if (collectionManager.getCollectionSize() != 0) {
            console.println("Объект Person с максимальным значением поля nationality: ")
            console.println(collectionManager.maxByNationality().toString())
        } else {
            console.printError("Коллекция пуста!")
        }
    }
}

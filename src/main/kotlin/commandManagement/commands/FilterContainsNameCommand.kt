package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CollectionManager

/**
 * Команда filter_contains_name. Выводит элементы, значение поля name которых содержит заданную подстроку.
 * @author dllnnx
 */
class FilterContainsNameCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command(
        "filter_contains_name",
        " name: вывести элементы, значение поля name которых содержит заданную подстроку."
    ) {
    /**
     * Выполнить команду
     * @param args аргумент команды
     */
    override fun execute(args: List<String?>) {
        if (args.size != 1) {
            console.printError("Неверное количество аргументов! Ожидалось: 1, введено: " + args.size + ".")
            return
        }
        if (collectionManager.getCollectionSize() == 0) {
            console.printError("Коллекция пуста!")
            return
        }

        val people = collectionManager.filterContainsName(args[0])
        if (people.isNotEmpty()) {
            console.println(people.toString())
        } else {
            console.printError("Нет элементов, значение поля name которых содержит данную подстроку :((")
        }
    }
}

package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CollectionManager

/**
 * Команда filter_by_height. Выводит элементы, значение поля height которых равно заданному.
 * @author dllnnx
 */
class FilterByHeightCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("filter_by_height", " height: вывести элементы, значение поля height которых равно заданному.") {
    /**
     * Выполнить команду
     * @param args аргумент команды
     */
    override fun execute(args: List<String?>) {
        try {
            if (args.size != 1) {
                console.printError("Неверное количество аргументов! Ожидалось: 1, введено: " + args.size + ".")
                return
            }
            if (collectionManager.getCollectionSize() == 0) {
                console.printError("Коллекция пуста!")
                return
            }

            val height = args[0]!!.toInt()
            val people = collectionManager.getByHeight(height)
            if (people.isNotEmpty()) {
                console.println(people.toString())
            } else {
                console.printError("Нет людей с таким ростом в коллекции!")
            }
        } catch (e: IllegalArgumentException) {
            console.printError("Рост должен быть целочисленным!")
        }
    }
}

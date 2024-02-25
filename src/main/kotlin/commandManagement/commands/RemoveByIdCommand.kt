package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import exceptions.NoSuchIdException
import managers.CollectionManager

/**
 * Команда remove_by_id. Удаляет элемент из коллекции по его id.
 * @author dllnnx
 */
class RemoveByIdCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("remove_by_id", " id: удалить элемент из коллекции по его id.") {
    /**
     * Выполнить команду
     * @param args аргумент команды
     */
    override fun execute(args: List<String?>) {
        try {
            if (args.size != 1) {
                console.printError("Неверное количество аргументов! Введено: " + args.size + ", ожидалось: 1.")
                return
            }

            if (collectionManager.getCollectionSize() != 0) {
                val id = args[0]!!.toLong()
                collectionManager.removeById(id)
                console.println(
                    ConsoleColor.setConsoleColor("Удаление элемента с id = $id произошло успешно!",
                        ConsoleColor.GREEN)
                )
            } else console.printError("Коллекция пуста!")
        } catch (e: NoSuchIdException) {
            console.printError(e.toString())
        } catch (e: IllegalArgumentException) {
            console.printError("id должен быть типа long!")
        }
    }
}

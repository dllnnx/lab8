package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CollectionManager
import objects.forms.PersonForm

/**
 * Команда update. Обновляет значение элемента коллекции, id которого равен заданному.
 * @author dllnnx
 */
class UpdateCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("update", " id: обновить значение элемента коллекции, id которого равен заданному.") {
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
            if (collectionManager.getCollectionSize() == 0) {
                console.printError("Коллекция пуста!")
                return
            }

            val id = args[0]!!.toLong()
            if (collectionManager.getById(id) != null) {
                console.println(
                    ConsoleColor.setConsoleColor("Обновление элемента Person, id = $id...", ConsoleColor.CYAN)
                )
                collectionManager.removeById(id)
                collectionManager.addElement(PersonForm(console, collectionManager).build(id))
                console.println(ConsoleColor.setConsoleColor("Элемент Person с id = $id обновлен успешно!",
                        ConsoleColor.GREEN))
            } else {
                console.printError("Нет элемента с таким id в коллекции!")
            }
        } catch (e: IllegalArgumentException) {
            console.printError("id должен быть типа long!")
        }
    }
}

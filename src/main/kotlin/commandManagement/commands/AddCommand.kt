package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import commandManagement.ConsoleColor
import managers.CollectionManager
import objects.forms.PersonForm

/**
 * Команда add {element}. Добавляет новый элемент в коллекцию.
 * @author dllnnx
 */
class AddCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("add", " {element}: добавить новый элемент в коллекцию.") {
    /**
     * Выполнить команду
     * @param args аргументы команды
     */
    override fun execute(args: List<String?>) {
        console.println(ConsoleColor.setConsoleColor("Создание элемента Person...", ConsoleColor.CYAN))
        collectionManager.addElement(PersonForm(console, collectionManager).build())
        console.println(ConsoleColor.setConsoleColor("Объект Person создан успешно!", ConsoleColor.GREEN))
    }
}

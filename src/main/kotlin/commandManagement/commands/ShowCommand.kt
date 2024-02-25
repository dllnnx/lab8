package commandManagement.commands

import commandManagement.Command
import commandManagement.Console
import managers.CollectionManager
import objects.Person

/**
 * Команда show. Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 * @author dllnnx
 */
class ShowCommand(private val console: Console, private val collectionManager: CollectionManager) :
    Command("show", ": вывести в стандартный поток вывода все элементы коллекции в строковом представлении.") {
    /**
     * Выполнить команду
     */
    override fun execute(args: List<String?>) {
        if (args.isEmpty()) {
            val collection: Collection<Person?> = collectionManager.collection
            if (collection.isEmpty()) {
                console.printError("Коллекция пуста!")
                return
            }
            for (person in collection){
                console.println(person.toString() + "\n")
            }
        } else console.printError("Для этой команды не требуются аргументы!")
    }
}

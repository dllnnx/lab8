package labs.server.commands

import shared.utility.Console
import server.utility.CollectionManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus
import shared.objects.Person
import java.util.LinkedList

/**
 * Команда show. Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 * @author dllnnx
 */
class ShowCommand(private val console: Console, private val collectionManager: CollectionManager) :
    labs.server.commands.Command("show", ": вывести в стандартный поток вывода все элементы коллекции в строковом представлении.") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isEmpty()) {
            val collection: LinkedList<Person?> = collectionManager.collection
            if (collection.isEmpty()) {
                return Response(ResponseStatus.WARNING, "Коллекция пуста!")
            }
            return Response(ResponseStatus.OK, "Элементы коллекции: \n", collection)
        } else return Response(ResponseStatus.WRONG_ARGUMENTS,"Для этой команды не требуются аргументы!")
    }
}

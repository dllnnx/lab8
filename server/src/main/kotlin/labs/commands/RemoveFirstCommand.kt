package labs.server.commands

import shared.utility.Console
import shared.utility.ConsoleColor
import server.utility.CollectionManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus

/**
 * Команда remove_first. Удаляет первый элемент из коллекции.
 * @author dllnnx
 */
class RemoveFirstCommand(private val console: Console, private val collectionManager: CollectionManager) :
    labs.server.commands.Command("remove_first", ": удалить первый элемент из коллекции.") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isBlank()) {
            if (collectionManager.getCollectionSize() != 0) {
                collectionManager.removeFirst()
                return Response(ResponseStatus.OK, "Первый элемент коллекции успешно удален!")
            } else return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        } else return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
    }
}

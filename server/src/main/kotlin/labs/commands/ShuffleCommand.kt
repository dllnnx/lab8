package labs.server.commands

import shared.utility.Console
import shared.utility.ConsoleColor
import server.utility.CollectionManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus

/**
 * Команда shuffle. Перемешивает элементы коллекции в случайном порядке.
 * @author dllnnx
 */
class ShuffleCommand(private val console: Console, private val collectionManager: CollectionManager) :
    labs.server.commands.Command("shuffle", ": перемешать элементы коллекции в случайном порядке.") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        if (collectionManager.getCollectionSize() != 0) {
            collectionManager.shuffle()
            return Response(ResponseStatus.OK, "\"Коллекция успешно перемешана!\"")
        } else {
            return Response(ResponseStatus.WARNING,"Коллекция пуста!")
        }
    }
}

package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда shuffle. Перемешивает элементы коллекции в случайном порядке.
 * @author dllnnx
 */
class ShuffleCommand(private val collectionManager: CollectionManager) :
    Command("shuffle", ": перемешать элементы коллекции в случайном порядке.") {
    override fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        if (collectionManager.getCollectionSize() != 0) {
            collectionManager.shuffle()
            return Response(ResponseStatus.OK, "Коллекция успешно перемешана!")
        } else {
            return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        }
    }
}

package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда clear. Очищает коллекцию
 * @author dllnnx
 */
class ClearCommand(private val collectionManager: CollectionManager) :
    Command("clear", ": очистить коллекцию.") {
    override fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        if (collectionManager.getCollectionSize() == 0) {
            return Response(ResponseStatus.WARNING, "Коллекция уже пуста!")
        }
        collectionManager.clearCollection()
        return Response(ResponseStatus.OK, "Коллекция успешно очищена!")
    }
}

package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда remove_first. Удаляет первый элемент из коллекции.
 * @author dllnnx
 */
class RemoveFirstCommand(private val collectionManager: CollectionManager) :
    Command("remove_first", ": удалить первый элемент из коллекции.") {
    override fun execute(request: Request): Response {
        if (request.args.isBlank()) {
            if (collectionManager.getCollectionSize() != 0) {
                collectionManager.removeFirst()
                return Response(ResponseStatus.OK, "Первый элемент коллекции успешно удален!")
            } else {
                return Response(ResponseStatus.WARNING, "Коллекция пуста!")
            }
        } else {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
    }
}

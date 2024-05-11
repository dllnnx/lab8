package labs.commands

import labs.database.DatabaseConnector
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
    override suspend fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        if (collectionManager.getCollectionSize() == 0) {
            return Response(ResponseStatus.WARNING, "Коллекция уже пуста!")
        }

        val ids =
            collectionManager
                .collection
                .filter { it!!.creatorLogin == request.user.login }
                .map { it!!.id }
                .toList()
        if (ids.isEmpty()) return Response(ResponseStatus.WARNING, "В коллекции нет Ваших элементов!")
        if (DatabaseConnector.personDatabase.deleteAllObjects(ids, request.user)) {
            collectionManager.removeElements(ids)
            return Response(ResponseStatus.OK, "Ваши элементы успешно удалены.")
        }
        return Response(ResponseStatus.ERROR, "Не удалось удалить элементы коллекции!")
    }
}

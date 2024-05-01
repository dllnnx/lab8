package labs.commands

import labs.database.DatabaseConnector
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
        if (request.args.isNotBlank()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        if (collectionManager.getCollectionSize() == 0) {
            return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        }

        try {
            val first = collectionManager
                .collection.first { it!!.creatorLogin == request.user!!.login }
            collectionManager.collection.remove(first)
            DatabaseConnector.databaseManager.deleteObjectById(first!!.id, request.user!!)
            return Response(ResponseStatus.OK, "Ваш первый элемент коллекции успешно удален!")
        } catch (e: NoSuchElementException) {
            return Response(ResponseStatus.ERROR, "В коллекции нет Ваших элементов!")
        } catch (e: Exception) {
            e.printStackTrace()
            return Response(ResponseStatus.ERROR, "В коллекции нет Ваших элементов!")
        }
    }
}

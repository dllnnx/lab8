package labs.commands

import labs.database.DatabaseConnector
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда remove_by_id. Удаляет элемент из коллекции по его id.
 * @author dllnnx
 */
class RemoveByIdCommand(private val collectionManager: CollectionManager) :
    Command("remove_by_id", " id: удалить элемент из коллекции по его id.") {
    override fun execute(request: Request): Response {
        if (request.args.isBlank()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды требуется аргумент!")
        }

        if (request.args.split(" ").size != 1) {
            return Response(
                ResponseStatus.WRONG_ARGUMENTS,
                "Неверное количество аргументов! " +
                    "Введено: " + request.args.split(" ").size + ", ожидалось: 1.",
            )
        }

        try {
            if (collectionManager.getCollectionSize() == 0) {
                return Response(ResponseStatus.WARNING, "Коллекция пуста!")
            }

            val id = request.args.trim().toLong()

            if (!collectionManager.checkExistById(id)) {
                return Response(ResponseStatus.ERROR, "Нет элемента с таким id в коллекции!")
            }

            if (DatabaseConnector.databaseManager.deleteObjectById(id, request.user!!)) {
                collectionManager.removeById(id)
                return Response(ResponseStatus.OK, "Удаление элемента с id = $id произошло успешно!")
            } else {
                return Response(ResponseStatus.ERROR, "Объект не удален. Удостоверьтесь, что он был создан Вами.")
            }
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.ERROR, "id должен быть типа long!")
        }
    }
}

package labs.commands

import labs.database.DatabaseConnector
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager
import java.util.Objects

/**
 * Команда update. Обновляет значение элемента коллекции, id которого равен заданному.
 * @author dllnnx
 */
class UpdateCommand(private val collectionManager: CollectionManager) :
    Command("update", " id: обновить значение элемента коллекции, id которого равен заданному.") {
    override fun execute(request: Request): Response {
        try {
            if (request.args.trim().split(" ").size != 1) {
                return Response(
                    ResponseStatus.WRONG_ARGUMENTS,
                    "Неверное количество аргументов! " +
                        "Введено: " + request.args.trim().split(" ").size + ", ожидалось: 1.",
                )
            }
            if (collectionManager.getCollectionSize() == 0) {
                return Response(ResponseStatus.WARNING, "Коллекция пуста!")
            }

            if (Objects.isNull(request.person)) {
                return Response(ResponseStatus.OBJECT_REQUIRED, "Для команды update требуется объект!")
            }

            val id = request.args.trim().split(" ")[0].toLong()
            if (collectionManager.getById(id) == null) {
                return Response(ResponseStatus.WARNING, "Нет элемента с таким id в коллекции!")
            }

            if (DatabaseConnector.databaseManager.updateObject(id, request.person!!)) {
                collectionManager.updateById(request.person, id)
                return Response(ResponseStatus.OK, "Элемент Person с id = $id обновлен успешно!")
            }

            return Response(ResponseStatus.ERROR, "Объект обновлен. Удостоверьтесь, что он был создан Вами.")
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.ERROR, "id должен быть типа long!")
        }
    }
}

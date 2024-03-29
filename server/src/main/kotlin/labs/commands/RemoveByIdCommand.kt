package labs.commands

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
        try {
            if (request.args.split(" ").size != 1) {
                return Response(
                    ResponseStatus.WRONG_ARGUMENTS,
                    "Неверное количество аргументов! " +
                        "Введено: " + request.args.split(" ").size + ", ожидалось: 1.",
                )
            }

            if (collectionManager.getCollectionSize() != 0) {
                val id = request.args.trim().toLong()
                return if (collectionManager.removeById(id)) {
                    Response(ResponseStatus.OK, "Удаление элемента с id = $id произошло успешно!")
                } else {
                    Response(ResponseStatus.WARNING, "Нет элемента с таким id в коллекции!")
                }
            } else {
                return Response(ResponseStatus.WARNING, "Коллекция пуста!")
            }
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.ERROR, "id должен быть типа long!")
        }
    }
}

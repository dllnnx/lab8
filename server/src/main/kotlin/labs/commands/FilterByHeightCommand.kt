package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда filter_by_height. Выводит элементы, значение поля height которых равно заданному.
 * @author dllnnx
 */
class FilterByHeightCommand(private val collectionManager: CollectionManager) :
    Command("filter_by_height", " height: вывести элементы, значение поля height которых равно заданному.") {

    override fun execute(request: Request) : Response {
        try {
            if (request.args.split(" ").size != 1) {
                return Response(
                    ResponseStatus.WRONG_ARGUMENTS, "Неверное количество аргументов! " +
                        "Ожидалось: 1, введено: " + request.args.split(" ").size + ".")
            }
            if (collectionManager.getCollectionSize() == 0) {
                return Response(ResponseStatus.ERROR, "Коллекция пуста!")
            }

            val height = request.args.split(" ")[0].toInt()
            val people = collectionManager.getByHeight(height)
            if (people.isNotEmpty()) {
                return Response(ResponseStatus.OK, "Отфильтрованная коллекция: ", people)
            } else {
                return Response(ResponseStatus.WARNING, "Нет людей с таким ростом в коллекции!")
            }
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.ERROR, "Рост должен быть целочисленным!")
        }
    }
}

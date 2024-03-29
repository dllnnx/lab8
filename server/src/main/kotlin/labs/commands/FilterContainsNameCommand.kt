package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда filter_contains_name. Выводит элементы, значение поля name которых содержит заданную подстроку.
 * @author dllnnx
 */
class FilterContainsNameCommand(private val collectionManager: CollectionManager) :
    Command(
        "filter_contains_name",
        " name: вывести элементы, значение поля name которых содержит заданную подстроку.",
    ) {
    override fun execute(request: Request): Response {
        if (request.args.split(" ").size != 1) {
            return Response(
                ResponseStatus.WRONG_ARGUMENTS,
                "Неверное количество аргументов! " +
                    "Ожидалось: 1, введено: " + request.args.split(" ").size + ".",
            )
        }
        if (collectionManager.getCollectionSize() == 0) {
            return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        }

        val people = collectionManager.filterContainsName(request.args.split(" ")[0].trim())
        if (people.isNotEmpty()) {
            return Response(ResponseStatus.OK, "Отфильтрованная коллекция: ", people)
        } else {
            return Response(ResponseStatus.WARNING, "Нет элементов, значение поля name которых содержит данную подстроку :((")
        }
    }
}

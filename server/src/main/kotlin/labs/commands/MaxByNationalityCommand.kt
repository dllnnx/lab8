package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда max_by_nationality. Выводит любой элемент из коллекции, значение поля nationality которого является максимальным.
 * @author dllnnx
 */
class MaxByNationalityCommand(private val collectionManager: CollectionManager) :
    Command(
        "max_by_nationality",
        ": вывести любой объект из коллекции, значение поля nationality которого является максимальным.",
    ) {
    override fun execute(request: Request): Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        if (collectionManager.getCollectionSize() != 0) {
            return Response(
                ResponseStatus.OK,
                "Объект Person с максимальным значением поля nationality: \n" +
                    collectionManager.maxByNationality().toString(),
            )
        } else {
            return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        }
    }
}

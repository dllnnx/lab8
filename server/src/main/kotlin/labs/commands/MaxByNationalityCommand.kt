package labs.server.commands

import shared.utility.Console
import server.utility.CollectionManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus

/**
 * Команда max_by_nationality. Выводит любой элемент из коллекции, значение поля nationality которого является максимальным.
 * @author dllnnx
 */
class MaxByNationalityCommand(private val console: Console, private val collectionManager: CollectionManager) :
    labs.server.commands.Command(
        "max_by_nationality",
        ": вывести любой объект из коллекции, значение поля nationality которого является максимальным."
    ) {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }

        if (collectionManager.getCollectionSize() != 0) {
            return Response(ResponseStatus.OK, "Объект Person с максимальным значением поля nationality: " +
                    collectionManager.maxByNationality().toString())
        } else {
            return Response(ResponseStatus.WARNING, "Коллекция пуста!")
        }
    }
}

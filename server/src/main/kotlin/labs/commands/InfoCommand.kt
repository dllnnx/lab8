package labs.server.commands

import shared.utility.Console
import server.utility.CollectionManager
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus

/**
 * Команда info. Выводит в стандартный поток вывода информацию о коллекции.
 * @author dllnnx
 */
class InfoCommand(private val console: Console, private val collectionManager: CollectionManager) :
    labs.server.commands.Command("info", ": вывести в стандартный поток вывода информацию о коллекции.") {
    /**
     * Выполнить команду
     */
    override fun execute(request: Request) : Response {
        if (request.args.isNotEmpty()) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Для этой команды не требуются аргументы!")
        }
        val resp =
            """
                Информация о коллекции: 
                Тип коллекции: ${collectionManager.getCollectionType()}
                Количество элементов: ${collectionManager.getCollectionSize()}
                Дата инициализации: ${collectionManager.initializationTime}
                """.trimIndent()
        return Response(ResponseStatus.OK, resp)
    }
}

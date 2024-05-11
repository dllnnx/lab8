package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager

/**
 * Команда info. Выводит в стандартный поток вывода информацию о коллекции.
 * @author dllnnx
 */
class InfoCommand(private val collectionManager: CollectionManager) :
    Command("info", ": вывести в стандартный поток вывода информацию о коллекции.") {
    /**
     * Выполнить команду
     */
    override suspend fun execute(request: Request): Response {
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

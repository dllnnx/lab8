package labs.commands

import labs.database.DatabaseConnector
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager
import labs.utility.CommandManager
import java.util.Objects

/**
 * Команда add {element}. Добавляет новый элемент в коллекцию.
 * @author dllnnx
 */
class AddCommand(private val collectionManager: CollectionManager, private val commandManager: CommandManager) :
    Command("add", " {element}: добавить новый элемент в коллекцию.") {
    override suspend fun execute(request: Request): Response {
        if (request.args.isNotBlank()) {
            return Response(
                ResponseStatus.WRONG_ARGUMENTS,
                "Для этой команды не требуются аргументы!",
            )
        }
        if (Objects.isNull(request.person)) {
            commandManager.removeLastCommand()
            return Response(ResponseStatus.OBJECT_REQUIRED, "Для команды $name требуется объект!")
        } else {
            val newId = DatabaseConnector.databaseManager.insertObject(request.person!!, request.user!!).toLong()
            if (newId == -1L) return Response(ResponseStatus.ERROR, "Не удалось добавить объект в базу данных.")
            request.person!!.id = newId
            request.person!!.creatorLogin = request.user!!.login
            collectionManager.addElement(request.person)
            return Response(ResponseStatus.OK, "Объект Person добавлен успешно!")
        }
    }
}

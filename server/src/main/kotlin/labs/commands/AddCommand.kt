package labs.commands

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.utility.CollectionManager
import java.util.Objects

/**
 * Команда add {element}. Добавляет новый элемент в коллекцию.
 * @author dllnnx
 */
class AddCommand(private val collectionManager: CollectionManager) :
    Command("add", " {element}: добавить новый элемент в коллекцию.") {
    override fun execute(request: Request): Response {
        if (request.args.isNotBlank()) {
            return Response(
                ResponseStatus.WRONG_ARGUMENTS,
                "Для этой команды не требуются аргументы!",
            )
        }
        if (Objects.isNull(request.person)) {
            return Response(ResponseStatus.OBJECT_REQUIRED, "Для команды $name требуется объект!")
        } else {
            request.person?.id = collectionManager.getFreeId()
            collectionManager.addElement(request.person)
            return Response(ResponseStatus.OK, "Объект Person добавлен успешно!")
        }
    }
}

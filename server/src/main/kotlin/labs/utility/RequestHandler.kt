package labs.utility

import labs.dto.Request
import labs.dto.Response

class RequestHandler(private val commandManager: CommandManager) {
    suspend fun handle(request: Request): Response {
        return commandManager.execute(request)
    }
}

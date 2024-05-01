package labs.commands

import labs.dto.Request
import labs.dto.Response

interface CommandInterface {
    suspend fun execute(request: Request): Response
}

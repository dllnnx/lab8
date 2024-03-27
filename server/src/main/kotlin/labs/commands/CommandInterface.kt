package labs.server.commands

import labs.dto.Request
import labs.dto.Response

interface CommandInterface {
    fun execute(request: Request) : Response
}

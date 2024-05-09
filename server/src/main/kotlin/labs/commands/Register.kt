package labs.commands

import labs.database.DatabaseConnector
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import org.apache.logging.log4j.kotlin.logger

class Register() : Command("register", ": зарегистрироваться") {
    private val logger = logger()

    override suspend fun execute(request: Request): Response {
        logger.info("Регистрация пользователя с логином: ${request.user.login}")
        val ins = DatabaseConnector.usersDatabase.insertUser(request.user)
        if (ins == -1) {
            return Response(ResponseStatus.ERROR, "Ошибка при регистрации!")
        }
        return Response(ResponseStatus.OK, "Регистрация прошла успешно!")
    }
}

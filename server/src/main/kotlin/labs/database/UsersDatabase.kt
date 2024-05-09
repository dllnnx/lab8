package labs.database

import labs.dto.User
import org.apache.logging.log4j.kotlin.logger
import java.security.MessageDigest
import java.sql.SQLException
import kotlin.random.Random

class UsersDatabase {
    private val connection = DatabaseConnector.databaseManager.connection
    private val logger = logger()

    private var md: MessageDigest = MessageDigest.getInstance("SHA-512")

    companion object {
        const val PEPPER = ",[0z/q{$.b*"
        const val SALT_CHARACTERS =
            "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "!@#$%^&*()_+<>?:;{}[]"
    }

    private fun checkUserExists(user: User): Boolean {
        return try {
            val ps = connection.prepareStatement(SQLCommands.getUser)
            ps.setString(1, user.login)
            val resultSet = ps.executeQuery()
            resultSet.next()
        } catch (e: SQLException) {
            logger.debug(e)
            logger.info("Ошибка при проверке существования пользователя в базе данных.")
            false
        }
    }

    fun insertUser(user: User): Int {
        try {
            val exists = checkUserExists(user)
            if (exists) {
                logger.info("Неуспешная регистрация: пользователь с таким логином уже существует.")
                return -1
            }

            val salt = generateSalt()
            val password = hasWith512SHA(PEPPER + user.password + salt)

            val ps = connection.prepareStatement(SQLCommands.insertUser)
            ps.setString(1, user.login)
            ps.setString(2, password)
            ps.setString(3, salt)
            val resultSet = ps.executeQuery()
            if (!resultSet.next()) {
                logger.error("Пользователь не добавлен в базу данных.")
                return -1
            }
            logger.info("Пользователь добавлен в базу данных.")
            return resultSet.getInt(1)
        } catch (e: SQLException) {
            logger.debug(e)
            logger.error("Ошибка при добавлении пользователя в базу данных.")
            return -1
        }
    }

    fun checkAuthUser(user: User): Boolean {
        try {
            val ps = connection.prepareStatement(SQLCommands.getUser)
            ps.setString(1, user.login)
            val resultSet = ps.executeQuery()
            if (!resultSet.next()) {
                return false
            }

            val salt = resultSet.getString("salt")
            val checkingPass = hasWith512SHA(PEPPER + user.password + salt)

            return resultSet.getString("password") == checkingPass
        } catch (e: SQLException) {
            logger.debug(e)
            logger.error("Ошибка при проверке существования пользователя!.")
            return false
        }
    }

    private fun generateSalt(): String {
        val rnd = Random
        var salt = ""
        for (i in 0..7) {
            salt += SALT_CHARACTERS[rnd.nextInt(SALT_CHARACTERS.length)]
        }
        return salt
    }

    private fun hasWith512SHA(string: String): String {
        md.update(string.toByteArray())
        val hashBytes = md.digest()
        var hashedString = ""
        for (byte in hashBytes) {
            hashedString += (String.format("%02x", byte))
        }
        return hashedString
    }
}

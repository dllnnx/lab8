package labs.database

import com.jcraft.jsch.JSch
import org.apache.logging.log4j.kotlin.logger
import java.sql.DriverManager

class DatabaseManager {
    init {
        val logger = logger()

        val jsch = JSch()
        val host = "se.ifmo.ru"
        val user = "s408536"
        val port = 2222
        val privateKey = "~/.ssh/id_rsa"

        val jdbcURL = "jdbc:postgresql://localhost:5432/studs"
        val dbHost = "pg"
        val dbUser = "s408536"
        val dbPassword = "u95MsTkmsVI2AaQ4"
        val localPort = 5432

        try {
            val session = jsch.getSession(user, host, port)

            session.setConfig("PreferredAuthentications", "publickey");
            jsch.setKnownHosts("~/.ssh/known_hosts")
            jsch.addIdentity(privateKey)
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect()

            session.setPortForwardingL(localPort, dbHost, 5432)

            System.setProperty("jdbc.url", jdbcURL)
            System.setProperty("jdbc.user", dbUser)

            Class.forName("org.postresql.Driver") // загрузка jdbc драйвера
            val connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)

            val statement = connection.createStatement()
            val resultSet = statement.executeQuery("SELECT * FROM device")
            while (resultSet.next()) {
                println(resultSet.getString("id"))
            }

            resultSet.close()
            statement.close()
            connection.close()

        } catch (e: ClassNotFoundException) {
            logger.error("Драйвер для JDBC не найден.")
        }

    }
}
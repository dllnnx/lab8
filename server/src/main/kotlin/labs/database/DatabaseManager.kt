package labs.database

import com.jcraft.jsch.JSch
import labs.Main
import org.apache.logging.log4j.kotlin.logger
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.Properties

class DatabaseManager {
    private val logger = logger()
    private val jsch = JSch()

    private val properties = Properties()

    lateinit var connection: Connection

    init {
        connectToDb()
        createMainBase()
    }

    private fun connectToDb() {
        try {
            properties.load(FileInputStream(Main.DATABASE_CONFIG_PATH))
            val session = jsch.getSession(properties.getProperty("user"), Main.DATABASE_HOST, Main.DATABASE_PORT)

            session.setConfig("PreferredAuthentications", "publickey")
            jsch.setKnownHosts(properties.getProperty("knownHosts"))
            jsch.addIdentity(properties.getProperty("privateKey"))
            session.setConfig("StrictHostKeyChecking", "no")
            session.connect()

            session.setPortForwardingL(Main.LOCAL_PORT, Main.JDBC_HOST, 5432)

            DriverManager.registerDriver(org.postgresql.Driver()) // загрузка jdbc драйвера
            connection = DriverManager.getConnection(Main.DATABASE_URL, properties.getProperty("user"), properties.getProperty("password"))
            logger.info("Подключение к базе данных успешно выполнено.")
        } catch (e: ClassNotFoundException) {
            logger.error("Драйвер для JDBC не найден.")
        }
    }

    private fun createMainBase() {
        try {
            connection.prepareStatement(SQLCommands.tablesCreation).execute()
            logger.info("В базе данных созданы таблицы.")
        } catch (e: SQLException) {
            logger.debug(e)
            logger.error("Неуспешная попытка создать таблицы в базе данных.")
        }
    }
}

package labs.database

import com.jcraft.jsch.JSch
import labs.Main
import labs.objects.Coordinates
import labs.objects.Country
import labs.objects.EyeColor
import labs.objects.HairColor
import labs.objects.Location
import labs.objects.Person
import org.apache.logging.log4j.kotlin.logger
import java.io.FileInputStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.sql.Types
import java.time.ZoneId
import java.util.LinkedList
import java.util.Properties

class DatabaseManager {
    private val logger = logger()
    private val jsch = JSch()

    private val properties = Properties()

    private lateinit var connection: Connection

    fun run() {
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
            logger.info("Неуспешная попытка создать таблицы в базе данных.")
        }
    }

    fun addObject(person: Person): Int {
        try {
            val ps = connection.prepareStatement(SQLCommands.addObject)
            fillSqlRequest(ps, person)
            ps.setString(12, "user1")
            val resultSet = ps.executeQuery()

            if (!resultSet.next()) {
                logger.info("Объект не добавлен в базу данных.")
                return -1
            }
            logger.info("Объект добавлен в базу данных.")
            return resultSet.getInt(1)
        } catch (e: SQLException) {
            logger.info("Неуспешная попытка добавить объект в базу данных.")
            logger.debug(e)
            return -1
        }
    }

    fun updateObject(
        id: Long,
        person: Person,
    ): Boolean {
        try {
            val ps = connection.prepareStatement(SQLCommands.updateUserObject)
            fillSqlRequest(ps, person)

            ps.setLong(12, id)
            ps.setString(13, "user1")
            val resultSet = ps.executeQuery()
            return resultSet.next()
        } catch (e: SQLException) {
            logger.debug(e)
            logger.info("Неуспешная попытка обновить объект.")
            return false
        }
    }

    fun deleteObjectById(id: Long): Boolean {
        try {
            val ps = connection.prepareStatement(SQLCommands.deleteUserCreatedObject)
            ps.setString(1, "user1")
            ps.setLong(2, id)
            val resultSet = ps.executeQuery()
            return resultSet.next()
        } catch (e: SQLException) {
            logger.debug(e)
            logger.info("Неуспешная попытка удалить объект.")
            return false
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun deleteAllObjects(ids: List<Long>): Boolean {
        try {
            for (id in ids) {
                val ps = connection.prepareStatement(SQLCommands.deleteUserCreatedObject)
                ps.setString(1, "user1")
                ps.setLong(2, id)
                ps.executeQuery()
            }
            logger.info("Удалены все объекты, созданные user1.")
            return true
        } catch (e: SQLException) {
            logger.debug(e)
            logger.info("Не удалось удалить все объекты, созданные user1.")
            return false
        }
    }

    fun fillCollection(): LinkedList<Person?> {
        try {
            val ps = connection.prepareStatement(SQLCommands.getAllObjects)
            val resultSet = ps.executeQuery()
            val collection = LinkedList<Person?>()
            while (resultSet.next()) {
                collection.add(
                    Person(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        Coordinates(
                            resultSet.getFloat("cord_x"),
                            resultSet.getDouble("cord_y"),
                        ),
                        resultSet.getTimestamp("creation_date").toLocalDateTime().atZone(ZoneId.systemDefault()),
                        resultSet.getInt("height"),
                        EyeColor.valueOf(resultSet.getString("eye_color")),
                        HairColor.valueOf(resultSet.getString("hair_color")),
                        Country.valueOf(resultSet.getString("nationality")),
                        Location(
                            resultSet.getFloat("location_x"),
                            resultSet.getFloat("location_y"),
                            resultSet.getString("location_name"),
                        ),
                    ),
                )
            }
            logger.info("Коллекция успешно загружена из базы данных.")
            return collection
        } catch (e: SQLException) {
            logger.debug(e)
            logger.info("Не удалось загрузить коллекцию: коллекция пуста либо возникла ошибка при исполнении запроса.")
            return LinkedList<Person?>()
        }
    }

    private fun fillSqlRequest(
        ps: PreparedStatement,
        person: Person,
    ): PreparedStatement {
        ps.setString(1, person.name)
        ps.setFloat(2, person.coordinates.x)
        ps.setDouble(3, person.coordinates.y)
        ps.setTimestamp(4, java.sql.Timestamp(person.creationDate.toInstant().toEpochMilli()))
        ps.setInt(5, person.height)
        ps.setObject(6, person.eyeColor, Types.OTHER)
        ps.setObject(7, person.hairColor, Types.OTHER)
        ps.setObject(8, person.nationality, Types.OTHER)
        ps.setFloat(9, person.location.x)
        ps.setFloat(10, person.location.y)
        ps.setString(11, person.location.name)
        return ps
    }
}

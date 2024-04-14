package labs.utility

import labs.dto.Request
import labs.exceptions.OpeningServerException
import org.apache.logging.log4j.kotlin.logger
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class Server(private val port: Int, private val handler: RequestHandler, private val fileManager: FileManager) {
    private val console: Printable = Console()
    private lateinit var serverSocketChannel: ServerSocketChannel
    private val bfReader = BufferedReader(InputStreamReader(BufferedInputStream(System.`in`)))
    private val selector = Selector.open()
    private var logger = logger()

    fun run() {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                fileManager.saveObjects()
                console.println(
                    ConsoleColor.setConsoleColor(
                        "Программа завершена. До свидания!))",
                        ConsoleColor.PURPLE,
                    ),
                )
                logger.info("Завершение работы сервера.")
            },
        )
        try {
            serverSocketChannel = ServerSocketChannel.open()
            serverSocketChannel.socket().bind(InetSocketAddress(port))
            serverSocketChannel.configureBlocking(false)
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)

            while (true) {
                if (bfReader.ready()) {
                    val line = bfReader.readLine()
                    if (line.equals("s")) {
                        fileManager.saveObjects()
                        logger.info("Коллекция сохранена.")
                    }
                }
                selector.select(50)
                val keys = selector.selectedKeys()
                keys.forEach { key ->
                    when {
                        key.isAcceptable -> {
                            acceptClient()
                            logger.info("Создано соединение с клиентом.")
                        }
                        key.isReadable -> {
                            processClientRequest(key)
                        }
                    }
                    keys.remove(key)
                }
            }
        } catch (e: IllegalArgumentException) {
            console.printError("Порт находится за пределами возможных значений! :((")
            logger.error("Порт находится за пределами возможных значений.")
            throw OpeningServerException()
        }
    }

    private fun acceptClient() {
        val clientChannel = serverSocketChannel.accept()
        clientChannel.configureBlocking(false)
        clientChannel.register(selector, SelectionKey.OP_READ or SelectionKey.OP_WRITE)
    }

    private fun getRequest(clientChannel: SocketChannel): Request? {
        val buffer = ByteBuffer.allocate(1024 * 4)
        while (true) {
            try {
                val bytesRead = clientChannel.read(buffer)
                if (bytesRead == -1) {
                    clientChannel.close()
                    return null
                }
                val ois = ObjectInputStream(ByteArrayInputStream(buffer.array()))
                val userRequest = ois.readObject() as Request
                ois.close()
                return userRequest
            } catch (_: StreamCorruptedException) {
                // если считали объект не до конца - пробуем еще раз
            }
        }
    }

    private fun processClientRequest(key: SelectionKey) {
        val clientChannel = key.channel() as SocketChannel
        clientChannel.configureBlocking(false)
        val userRequest = getRequest(clientChannel)
        logger.info("Получен запрос от клиента.")
        if (userRequest == null) return

        val responseToUser = handler.handle(userRequest)
        logger.info("Запрос обработан.")
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(responseToUser)
        oos.flush()
        val responseData = baos.toByteArray()
        clientChannel.write(ByteBuffer.wrap(responseData))
        logger.info("Отправлен ответ клиенту.")
    }
}

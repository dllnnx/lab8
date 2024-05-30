// я решила отчислиться 3 раза пока писала этот класс
package labs.utility

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import org.apache.logging.log4j.kotlin.logger
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.StreamCorruptedException
import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import kotlin.system.exitProcess

class Server(private val port: Int, private val handler: RequestHandler) {
    private val console: Printable = Console()
    private lateinit var serverSocketChannel: ServerSocketChannel
    private val selector = Selector.open()
    private var logger = logger()
    private val channelCapacity = 10

    fun run() =
        runBlocking {
            try {
                serverSocketChannel = ServerSocketChannel.open()
                serverSocketChannel.socket().bind(InetSocketAddress(port))
                serverSocketChannel.configureBlocking(false)

                // каналы для связи между корутинами
                val requestChannel = Channel<Pair<SocketChannel, Request>>(channelCapacity)
                val responseChannel = Channel<Pair<SocketChannel, Response>>(channelCapacity)

                // чтение запроса
                launch(Dispatchers.Default) {
                    while (true) {
                        val ready =
                            withContext(Dispatchers.IO) {
                                selector.select(50)
                            }
                        if (ready == 0) continue

                        val keys = selector.selectedKeys().iterator()
                        while (keys.hasNext()) {
                            val key = keys.next()
                            if (key.isValid && key.readyOps() == SelectionKey.OP_READ) {
                                val clientChannel = key.channel() as SocketChannel
                                val userRequest = getRequest(clientChannel)
                                if (userRequest!!.commandName != "login" && userRequest.commandName != "register" &&
                                    userRequest.commandName != "exit" && userRequest.commandName != "show"
                                ) {
                                    logger.info("Получен запрос от пользователя ${userRequest.user.login}.")
                                }
                                requestChannel.send(Pair(clientChannel, userRequest))
                            }
                            keys.remove()
                        }
                    }
                }

                // обработка запроса
                launch(Dispatchers.Default) {
                    for (pair in requestChannel) {
                        val clientChannel = pair.first
                        val userRequest = pair.second
                        val responseToUser = handler.handle(userRequest)
                        if (userRequest.commandName != "login" && userRequest.commandName != "register" &&
                            userRequest.commandName != "exit" && userRequest.commandName != "show"
                        ) {
                            logger.info("Запрос пользователя ${userRequest.user.login} обработан.")
                        }
                        responseChannel.send(Pair(clientChannel, responseToUser))
                    }
                }

                // отправка ответа
                launch(Dispatchers.Default) {
                    for (pair in responseChannel) {
                        val clientChannel = pair.first
                        val responseToUser = pair.second
                        if (responseToUser.status == ResponseStatus.EXIT) {
                            withContext(Dispatchers.IO) {
                                clientChannel.close()
                            }
                        }
                        try {
                            sendResponse(clientChannel, responseToUser)
                            logger.info("Отправлен ответ клиенту.")
                        } catch (_: IOException) {
                            // такие вылезают когда клиент отключается. забиваем хуй на это пусть отключается спокойно
                        }
                    }
                }

                // соединение с клиентами
                while (true) {
                    val clientChannel = acceptClient() ?: continue
                    clientChannel.configureBlocking(false)
                    logger.info("Создано соединение с клиентом.")
                }
            } catch (e: IllegalArgumentException) {
                console.printError("Порт находится за пределами возможных значений! :((")
                logger.error("Порт находится за пределами возможных значений.")
                exitProcess(0)
            }
        }

    private fun acceptClient(): SocketChannel? {
        val clientChannel = serverSocketChannel.accept()
        clientChannel?.configureBlocking(false)
        clientChannel?.register(selector, SelectionKey.OP_READ)
        return clientChannel
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
            } catch (e: StreamCorruptedException) {
                // если считали объект не до конца - пробуем еще раз
            } catch (e: IOException) {
                // если клиентик отключается
                return Request("exit")
            }
        }
    }

    private fun sendResponse(
        clientChannel: SocketChannel,
        responseToUser: Response,
    ) {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(responseToUser)
        oos.flush()
        val responseData = baos.toByteArray()
        clientChannel.write(ByteBuffer.wrap(responseData))
    }
}

package labs.utility

import labs.dto.Request
import labs.dto.Response
import labs.exceptions.OpeningServerException
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.InetSocketAddress
import java.net.SocketAddress
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel
import java.util.concurrent.TimeoutException

class Server(private val port: Int, private val handler: RequestHandler, private val fileManager: FileManager) {
    private var socketChannel: SocketChannel? = null
    private val console: Printable = FileConsole()
    private lateinit var serverSocketChannel: ServerSocketChannel
    private val bf = BufferedInputStream(System.`in`)
    private val bfReader = BufferedReader(InputStreamReader(bf))
//    private lateinit var selector: Selector

    private fun openServerSocket() {
        try {
            val socketAddress: SocketAddress = InetSocketAddress(port)
//            selector = Selector.open()
            serverSocketChannel = ServerSocketChannel.open()
//            serverSocketChannel.configureBlocking(false)
            serverSocketChannel.bind(socketAddress)
//            serverSocketChannel.register(selector, SelectionKey.OP_CONNECT)
        } catch (e: IllegalArgumentException) {
            console.printError("Порт находится за пределами возможных значений! :((")
            throw OpeningServerException()
        }
    }

    private fun connectToClient(): SocketChannel {
        serverSocketChannel.socket().soTimeout = 50;
        socketChannel = serverSocketChannel.socket().accept().channel
        console.println("Соединение с клиентом успешно установлено!")
        return socketChannel!!
    }

    private fun processClientRequest(clientSocket: SocketChannel): Boolean {
        var userRequest: Request? = null
        var responseToUser: Response

        try {
            val clientReader = ObjectInputStream(clientSocket.socket().getInputStream())
            val clientWriter = ObjectOutputStream(clientSocket.socket().getOutputStream())
            while (true) {
                userRequest = clientReader.readObject() as Request
                responseToUser = handler.handle(userRequest)
                clientWriter.writeObject(responseToUser)
                clientWriter.flush()
            }
        } catch (e: IOException) {
            if (userRequest == null) {
                console.printError("Произошел непредвиденный разрыв соединения с сервером :((")
            } else {
                console.println("Соединение с сервером успешно разорвано!")
            }
        }
        return true
    }

    private fun stop() {
        try {
            if (socketChannel == null) {
                console.printError("Нельзя прекратить работу незапущенного серверая!")
            }
            socketChannel!!.close()
            serverSocketChannel.close()
        } catch (e: IOException) {
            console.printError("Ошибка при завершении работы сервера!")
        }
    }

    fun run() {
        try {
            openServerSocket()
            while (true) {
                if (bfReader.ready()) {
                    val line = bfReader.readLine()
                    if (line.equals("save")) fileManager.saveObjects()
                }

                try {
                    val clientSocket: SocketChannel = connectToClient()
                    clientSocket.use {
                        if (!processClientRequest(clientSocket)) {
                            stop()
                            return
                        }
                    }
                } catch (_: TimeoutException) {
                    // если не поступил запрос от клиента -> ждем дальше
                } catch (e: IOException) {
                    console.printError("Ошибка при попытке завершить соединение с клиентом!")
                }
            }
        } catch (e: OpeningServerException) {
            console.printError("Сервер не может быть запущен! :((((( ")
        }
    }
}

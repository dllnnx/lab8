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
import java.nio.channels.ServerSocketChannel
import java.nio.channels.SocketChannel

class Server(private val port: Int, private val handler: RequestHandler, private val fileManager: FileManager) {
    private var socketChannel: SocketChannel? = null
    private val console: Printable = FileConsole()
    private lateinit var serverSocketChannel: ServerSocketChannel
    private val bf = BufferedInputStream(System.`in`)
    private val bfReader = BufferedReader(InputStreamReader(bf))

    private fun openServerSocket() {
        try {
            val socketAddress: SocketAddress = InetSocketAddress(port)
            serverSocketChannel = ServerSocketChannel.open()
            serverSocketChannel.bind(socketAddress)
        } catch (e: IllegalArgumentException) {
            console.printError("Порт находится за пределами возможных значений! :((")
            throw OpeningServerException()
        }
    }

    private fun connectToClient(): SocketChannel {
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
                console.printError("оо нет разрыв")
            } else {
                console.println("ооо даа разрыв")
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
                try {
                    if (bfReader.ready()) {
                        val line = bfReader.readLine()
                        if (line.equals("save")) fileManager.saveObjects()
                    }
                } catch (_: IOException) {
                }
                val clientSocket: SocketChannel = connectToClient()
                try {
                    clientSocket.use {
                        if (!processClientRequest(clientSocket)) {
                            stop()
                            return
                        }
                    }
                } catch (e: IOException) {
                    console.printError("Ошибка при попытке завершить соединение с клиентом!")
                }
            }
        } catch (e: OpeningServerException) {
            console.printError("Сервер не может быть запущен! :((((( ")
        }
    }
}

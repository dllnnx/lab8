package labs.utility

import labs.Main
import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.dto.User
import tornadofx.Controller
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.Socket
import java.util.Objects

class Client: Controller() {
    private var host: String = Main.host
    private var port = Main.port
    private var reconnectionTimeout = 2000L
    private var maxReconnectionAttempts = 5
    private var socket: Socket? = null
    private var serverWriter: ObjectOutputStream? = null
    private var serverReader: ObjectInputStream? = null
    private var reconnectionAttempts = 0
    var user: User = User("", "")

    private fun connectToServer() {
        socket = Socket(host, port)
    }

    private fun disconnectFromServer() {
        if (!Objects.isNull(socket)) {
            socket!!.close()
            serverWriter!!.close()
            serverReader!!.close()
            serverWriter = null
            serverReader = null
        }
    }

    fun sendAndReceiveResponse(request: Request): Response {
        while (true) {
            try {
                if (Objects.isNull(socket)) {
                    connectToServer()
                } else {
                    if (request.isEmpty()) return Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой :(")
                    serverWriter = ObjectOutputStream(socket!!.getOutputStream())
                    serverWriter!!.writeObject(request)
                    serverWriter!!.flush()
                    serverReader = ObjectInputStream(socket!!.getInputStream())
                    val response = serverReader!!.readObject() as Response
                    reconnectionAttempts = 0
                    return response
                }
            } catch (e: Exception) {
                try {
                    when (e) {
                        is ConnectException, is IOException -> {
                            if (reconnectionAttempts == 0) println("Разорвано соединение с сервером!")
                            reconnectionAttempts++
                            if (reconnectionAttempts >= maxReconnectionAttempts) {
                                println("Превышено максимальное количество попыток соединения с сервером.")
                                disconnectFromServer()
                                return Response(ResponseStatus.EXIT)
                            }

                            println(
                                ConsoleColor.setConsoleColor(
                                    "Повторная попытка подключения через $reconnectionTimeout ms",
                                    ConsoleColor.YELLOW,
                                ),
                            )
                            Thread.sleep(reconnectionTimeout)
                            connectToServer()
                            reconnectionAttempts = 0
                        }
                    }
                } catch (e: ConnectException) {
                    println("Попытка соединения с сервером неуспешна.")
                }
            }
        }
    }
}

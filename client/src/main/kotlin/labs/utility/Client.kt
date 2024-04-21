package labs.utility

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.ConnectException
import java.net.Socket
import java.util.Objects

class Client(
    private val host: String,
    private val port: Int,
    private val reconnectionTimeout: Long,
    private val maxReconnectionAttempts: Int,
    private val console: Printable,
) {
    private var socket: Socket? = null
    private var serverWriter: ObjectOutputStream? = null
    private var serverReader: ObjectInputStream? = null
    private var reconnectionAttempts = 0

    private fun connectToServer() {
        socket = Socket(host, port)
    }

    fun sendAndReceiveResponse(request: Request): Response {
        while (true) {
            try {
                if (Objects.isNull(socket)) {
                    connectToServer()
                } else {
                    reconnectionAttempts = 0
                    if (request.isEmpty()) return Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой :(")
                    serverWriter = ObjectOutputStream(socket!!.getOutputStream())
                    serverWriter!!.writeObject(request)
                    serverWriter!!.flush()
                    serverReader = ObjectInputStream(socket!!.getInputStream())
                    val response = serverReader!!.readObject() as Response
                    return response
                }
            } catch (e: Exception) {
                try {
                    when (e) {
                        is ConnectException, is IOException -> {
                            if (reconnectionAttempts == 0) console.printError("Разорвано соединение с сервером!")
                            reconnectionAttempts++
                            if (reconnectionAttempts >= maxReconnectionAttempts) {
                                console.printError("Превышено максимальное количество попыток соединения с сервером.")
                                return Response(ResponseStatus.EXIT)
                            }

                            console.println(
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
                    console.printError("Попытка соединения с сервером неуспешна.")
                }
            }
        }
    }
}

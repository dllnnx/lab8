package labs.utility

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.Objects

class Client(private val host: String, private val port: Int) {
    private lateinit var socket: Socket
    private var serverWriter: ObjectOutputStream? = null
    private var serverReader: ObjectInputStream? = null

    private fun connectToServer() {
        socket = Socket(host, port)
        serverWriter = ObjectOutputStream(socket.getOutputStream())
    }

    private fun disconnectFromServer() {
        socket.close()
        serverWriter!!.close()
        serverReader!!.close()
        serverWriter = null
        serverReader = null
    }

    fun sendAndReceiveResponse(request: Request): Response {
        while (true) {
            try {
                if (Objects.isNull(serverWriter)) {
                    connectToServer()
                } else {
                    if (request.isEmpty()) return Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой :(")
                    serverWriter!!.writeObject(request)
                    serverWriter!!.flush()
                    serverReader = ObjectInputStream(socket.getInputStream())
                    val response = serverReader!!.readObject() as Response
                    disconnectFromServer()
                    return response
                }
            } catch (e: IOException) {
                print(e)
                connectToServer()
            }
        }
    }
}

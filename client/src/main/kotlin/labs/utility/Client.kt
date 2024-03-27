package labs.client.utility

import shared.utility.Printable
import shared.dto.Request
import shared.dto.Response
import shared.dto.ResponseStatus
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.*

class Client (host: String, port: Int, console: Printable) {
    val host = host
    val port = port
    lateinit var socket: Socket
    lateinit var serverWriter: ObjectOutputStream
    lateinit var serverReader: ObjectInputStream

    fun connectToServer(){
        socket = Socket(host, port)
        serverWriter = ObjectOutputStream(socket.getOutputStream())
        serverReader = ObjectInputStream(socket.getInputStream())
    }

    fun disconnectFromServer(){
        socket.close()
        serverWriter.close()
        serverReader.close()
    }

    fun sendAndReceiveResponse(request: Request): Response {
        while(true){
            if (Objects.isNull(serverReader) || Objects.isNull(serverWriter)){
                connectToServer()
            } else {
                if (request.isEmpty()) return Response(ResponseStatus.WRONG_ARGUMENTS, "Запрос пустой :(")
                serverWriter.writeObject(request)
                serverWriter.flush()
                val response = serverReader.readObject() as Response
                disconnectFromServer()
                return response;
            }
        }
    }
}
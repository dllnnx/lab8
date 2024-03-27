package labs

import labs.utility.Client
import labs.utility.Console
import labs.utility.RuntimeManager
import java.util.*

object Main {
    val console = Console()
    lateinit var host : String
    var port : Int = 0

    @JvmStatic
    fun main(args: Array<String>){
        if (!parseHostPort(args)) return
        val client = Client(host, port, console)
        RuntimeManager(console, Scanner(System.`in`), client).interactiveMode()
    }

    private fun parseHostPort(args: Array<String>) : Boolean{
        if (args.size != 2) {
            console.printError("Передайте хост и порт в аргументы командной строки в формате <host> <port>")
            return false
        }
        host = args[0]
        port = args[1].toInt()
        if (port < 0) {
            console.printError("Порт должен быть натуральным числом")
            return false
        }
        return true
    }
}
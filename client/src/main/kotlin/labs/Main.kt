package labs

import javafx.application.Application
import labs.ui.ClientApp
import labs.utility.Client
import labs.utility.Console
import labs.utility.ScriptManager

object Main {
    private val console = Console()
    private val scriptManager = ScriptManager()
    lateinit var host: String
    var port: Int = 0
    lateinit var client: Client

    @JvmStatic
    fun main(args: Array<String>) {
        if (!parseHostPort(args)) return
        Application.launch(ClientApp::class.java, *args)
//        RuntimeManager(console, Scanner(System.`in`), client, scriptManager).interactiveMode()
    }

    private fun parseHostPort(args: Array<String>): Boolean {
        if (args.size != 2) {
            console.printError("Передайте хост и порт в аргументы командной строки в формате <host> <port>!")
            return false
        }
        try {
            host = args[0]
            port = args[1].toInt()
            if (port < 0) throw NumberFormatException()
        } catch (e: NumberFormatException) {
            console.printError("Порт должен быть натуральным числом!")
            return false
        }
        return true
    }
}

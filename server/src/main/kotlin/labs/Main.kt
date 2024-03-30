package labs

import labs.commands.AddCommand
import labs.commands.ClearCommand
import labs.commands.ExitCommand
import labs.commands.FilterByHeightCommand
import labs.commands.FilterContainsNameCommand
import labs.commands.HelpCommand
import labs.commands.HistoryCommand
import labs.commands.InfoCommand
import labs.commands.MaxByNationalityCommand
import labs.commands.RemoveByIdCommand
import labs.commands.RemoveFirstCommand
import labs.commands.ShowCommand
import labs.commands.ShuffleCommand
import labs.commands.UpdateCommand
import labs.utility.CollectionManager
import labs.utility.CommandManager
import labs.utility.Console
import labs.utility.FileManager
import labs.utility.RequestHandler
import labs.utility.Server
import kotlin.properties.Delegates

object Main {
    private var port by Delegates.notNull<Int>()
    private var console = Console()

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            try {
                port = args[0].toInt()
                if (port < 0) throw NumberFormatException()
            } catch (e: NumberFormatException) {
                console.printError("Порт должен быть натуральным числом!")
                return
            }
        } else {
            console.printError("Передайте порт в аргументы командной строки!")
            return
        }

        val collectionManager = CollectionManager()
        val fileManager = FileManager(console, collectionManager)
        fileManager.fillCollection()

        val commandManager = CommandManager()
        commandManager.addCommands(
            listOf(
                HelpCommand(commandManager),
                InfoCommand(collectionManager),
                AddCommand(collectionManager),
                ShowCommand(collectionManager),
                UpdateCommand(collectionManager),
                RemoveByIdCommand(collectionManager),
                ClearCommand(collectionManager),
                RemoveFirstCommand(collectionManager),
                FilterByHeightCommand(collectionManager),
                FilterContainsNameCommand(collectionManager),
                ExitCommand(),
                MaxByNationalityCommand(collectionManager),
                ShuffleCommand(collectionManager),
                HistoryCommand(commandManager),
            ),
        )

        val requestHandler = RequestHandler(commandManager)
        val server = Server(port, requestHandler, fileManager)
        server.run()
    }
}

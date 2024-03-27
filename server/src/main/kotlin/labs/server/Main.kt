package labs.server

import client.utility.RuntimeManager
import server.utility.ScriptManager
import shared.utility.Console
import server.commands.*
import server.utility.CollectionManager
import server.utility.CommandManager
import server.utility.FileManager
import java.io.File

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        System.setProperty("file_path", File("data.json").absolutePath)
        val console = Console()
        val commandManager = CommandManager()
        val collectionManager = CollectionManager()
        val fileManager = FileManager(console, collectionManager)
        val scriptManager = ScriptManager()
        commandManager.addCommands(
            listOf(
                labs.server.commands.HelpCommand(console, commandManager),
                labs.server.commands.InfoCommand(console, collectionManager),
                labs.server.commands.AddCommand(console, collectionManager),
                ShowCommand(console, collectionManager),
                UpdateCommand(console, collectionManager),
                RemoveByIdCommand(console, collectionManager),
                labs.server.commands.ClearCommand(console, collectionManager),
                RemoveFirstCommand(console, collectionManager),
                labs.server.commands.FilterByHeightCommand(console, collectionManager),
                labs.server.commands.FilterContainsNameCommand(console, collectionManager),
                labs.server.commands.ExitCommand(console),
                labs.server.commands.MaxByNationalityCommand(console, collectionManager),
                ShuffleCommand(console, collectionManager),
                labs.server.commands.HistoryCommand(console, commandManager),
                SaveCommand(fileManager, console),
                labs.server.commands.ExecuteScriptCommand(console, commandManager, scriptManager)
            )
        )
        RuntimeManager(console, commandManager, fileManager).interactiveMode()
    }
}

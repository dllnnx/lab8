//package labs.server
//
//import client.utility.RuntimeManager
//import labs.commands.*
//import labs.utility.CollectionManager
//import labs.utility.CommandManager
//import labs.utility.Console
//import labs.utility.FileManager
//import server.utility.ScriptManager
//import shared.utility.Console
//import server.commands.*
//import server.utility.CollectionManager
//import server.utility.CommandManager
//import server.utility.FileManager
//import java.io.File
//
//object Main {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        System.setProperty("file_path", File("data.json").absolutePath)
//        val console = Console()
//        val commandManager = CommandManager()
//        val collectionManager = CollectionManager()
//        val fileManager = FileManager(console, collectionManager)
//        val scriptManager = ScriptManager()
//        commandManager.addCommands(
//            listOf(
//                HelpCommand(console, commandManager),
//                InfoCommand(console, collectionManager),
//                AddCommand(console, collectionManager),
//                ShowCommand(console, collectionManager),
//                UpdateCommand(console, collectionManager),
//                RemoveByIdCommand(console, collectionManager),
//                ClearCommand(console, collectionManager),
//                RemoveFirstCommand(console, collectionManager),
//                FilterByHeightCommand(console, collectionManager),
//                FilterContainsNameCommand(console, collectionManager),
//                ExitCommand(console),
//                MaxByNationalityCommand(console, collectionManager),
//                ShuffleCommand(console, collectionManager),
//                HistoryCommand(console, commandManager),
//                SaveCommand(fileManager, console),
//                ExecuteScriptCommand(console, commandManager, scriptManager)
//            )
//        )
//        RuntimeManager(console, commandManager, fileManager).interactiveMode()
//    }
//}

package labs

import labs.commands.*
import labs.utility.*

object Main {

    var PORT = 6086
    private var console = Console()
    @JvmStatic
    fun main(args: Array<String>){
        if (args.isNotEmpty()){
            try{
                PORT = args[0].toInt()
            } catch (_: NumberFormatException){}
        }

        val collectionManager = CollectionManager()
        val fileManager = FileManager(console, collectionManager)
        fileManager.fillCollection()

        val commandManager = CommandManager()
        commandManager.addCommands(listOf(
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
        ))

        val requestHandler = RequestHandler(commandManager)
        val server = Server(PORT, requestHandler, fileManager)
        server.run()
    }


}
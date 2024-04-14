package labs

import labs.commands.AddCommand
import labs.commands.ClearCommand
import labs.commands.ExecuteScriptCommand
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
import org.apache.logging.log4j.kotlin.logger
import java.io.File
import kotlin.properties.Delegates

object Main {
    private var port by Delegates.notNull<Int>()
    private var console = Console()
    private var logger = logger()

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isNotEmpty()) {
            try {
                port = args[0].toInt()
                if (port < 0) throw NumberFormatException()
            } catch (e: NumberFormatException) {
                console.printError("Порт должен быть натуральным числом!")
                logger.error("Неправильное значение порта. Завершение работы.")
                return
            }
        } else {
            console.printError("Передайте порт в аргументы командной строки!")
            logger.error("В аргументы командной строки не введен порт. Завершение работы.")
            return
        }
        logger.info("Порт успешно получен из аргументов командной строки.")

        logger.info("Создание объектов...")
        System.setProperty("file_path", File("data.json").absolutePath)
        val collectionManager = CollectionManager()
        val fileManager = FileManager(console, collectionManager)
        fileManager.fillCollection()
        logger.info("Коллекция успешно заполнена объектами из файла.")

        val commandManager = CommandManager()
        commandManager.addCommands(
            listOf(
                HelpCommand(commandManager),
                InfoCommand(collectionManager),
                AddCommand(collectionManager, commandManager),
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
                ExecuteScriptCommand(),
            ),
        )
        val requestHandler = RequestHandler(commandManager)
        val server = Server(port, requestHandler, fileManager)
        logger.info("Создан объект сервера.")
        server.run()
    }
}

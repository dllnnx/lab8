// package server.commands
//
// import shared.utility.Console
// import server.utility.FileManager
//
// /**
// * Команда save. Сохраняет коллекцию в файл.
// * @author dllnnx
// */
// class SaveCommand(private val fileManager: FileManager, private val console: Console) :
//    Command("save", ": сохранить коллекцию в файл.") {
//    /**
//     * Выполнить команду
//     */
//    override fun execute(args: List<String?>) {
//        if (args.isEmpty()) {
//            fileManager.saveObjects()
//        } else console.printError("Для этой команды не требуются аргументы!")
//    }
// }

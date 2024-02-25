package managers

import commandManagement.Command

/**
 * Командный менеджер.
 * @author dllnnx
 */
class CommandManager {
    /**
     * Поле для хранения команд в формате Имя: Команда
     */
    var commands = HashMap<String, Command>()

    /**
     * Поле для хранения истории команд
     */
    var commandHistory = ArrayList<String>()


    /**
     * Добавляет новые команды в список исполняемых команд.
     * @param commands Команды для добавления
     */
    fun addCommands(commands: Collection<Command>) {
        this.commands.putAll(commands.associateBy { it.name })
    }

    /**
     * Выполняет команду
     * @param name название команды
     * @param args аргументы команды
     */
    fun execute(name: String, args: List<String?>) {
        val command = commands[name]
        command!!.execute(args)
    }

    /**
     * Добавляет команду в историю команд
     * @param command Имя команды
     */
    fun addToHistory(command: String) {
        commandHistory.add(command)
    }
}

package commandManagement

/**
 * Интерфейс для исполняемых команд.
 * @author dllnnx
 */
interface CommandInterface {
    /**
     * Исполняет команду
     * @param args аргументы команды
     */
    fun execute(args: List<String?>)
}

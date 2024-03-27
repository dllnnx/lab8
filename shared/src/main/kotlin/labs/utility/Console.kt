package labs.shared.utility

/**
 * Класс для вывода в стандартную консоль.
 * @author dllnnx
 */
class Console : Printable {
    /**
     * Выводит аргумент в консоль
     * @param s аргумент для вывода
     */
    override fun print(s: String) {
        kotlin.io.print(s)
    }

    /**
     * Выводит аргумент в консоль с новой строки
     * @param s аргумент для вывода
     */
    override fun println(s: String) {
        kotlin.io.println(s)
    }

    /**
     * Выводит ошибки в консоль
     * @param s ошибка для печати
     */
    override fun printError(s: String) {
        kotlin.io.println(ConsoleColor.setConsoleColor(s, ConsoleColor.RED))
    }

    companion object {
        /**
         * Хранит состояние, в котором находится режим ввода
         */
        var fileMode = false
    }
}

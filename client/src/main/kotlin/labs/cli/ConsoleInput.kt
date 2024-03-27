package labs.cli

import labs.utility.ScannerManager
import java.util.*

/**
 * Класс для ввода через стандартную консоль.
 * @author dllnnx
 */
class ConsoleInput : UserInput {
    /**
     * Считывает следующую строку ввода
     * @return Введенная строка
     */
    override fun nextLine(): String {
        return userScanner.nextLine()
    }

    companion object {
        /**
         * Текущий объект класса Scanner
         */
        private val userScanner: Scanner = ScannerManager.userScanner
    }
}

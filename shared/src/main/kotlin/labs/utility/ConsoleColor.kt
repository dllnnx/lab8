package labs.utility

/**
 * Класс для присваивания цвета тексту в консоли.
 * @author dllnnx
 */
enum class ConsoleColor(private val color: String) {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    GREY("\u001B[37m");

    override fun toString(): String {
        return color
    }

    companion object {
        /**
         * Присваивает цвет тексту в консоли.
         * @param msg Строка для вывода
         * @param color Присваиваемый цвет
         */
        fun setConsoleColor(msg: String, color: ConsoleColor): String {
            return color.toString() + msg + RESET
        }
    }
}

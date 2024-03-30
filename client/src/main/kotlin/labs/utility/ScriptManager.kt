package labs.utility

import labs.cli.UserInput
import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayDeque
import java.util.Scanner

class ScriptManager : UserInput {
    /**
     * Считывает следующую строку ввода
     * @return Строка ввода если есть, "" иначе
     */
    override fun nextLine(): String? {
        return try {
            scanners.getLast().nextLine()
        } catch (e: NoSuchElementException) {
            ""
        }
    }

    companion object {
        /**
         * Поле для хранения путей к запускаемым файлам
         */
        private val pathQueue = ArrayDeque<String>()

        /**
         * Поле для хранения экземпляров сканеров
         */
        private val scanners = ArrayDeque<Scanner>()

        /**
         * Добавляет файл в список запускаемых файлов
         * @param path Путь к файлу
         * @throws FileNotFoundException Выбрасывается, если файл не найден
         */
        @Throws(FileNotFoundException::class)
        fun addFile(path: String?) {
            pathQueue.add(File(path!!).absolutePath)
            scanners.add(Scanner(File(path)))
        }

        /**
         * Проверяет, нет ли рекурсивного запуска скриптов
         * @param path Путь к проверяемому на рекурсию файлу
         * @return true, если запуск рекурсивный, иначе false
         */
        fun isRecursive(path: String?): Boolean {
            return pathQueue.contains(File(path!!).absolutePath)
        }

        /**
         * Удаляет файл из списка запускаемых файлов
         */
        fun removeFile() {
            scanners.removeLast()
            pathQueue.removeLast()
        }
    }
}

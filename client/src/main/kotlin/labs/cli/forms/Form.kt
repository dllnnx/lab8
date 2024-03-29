package labs.cli.forms

import labs.cli.ConsoleInput
import labs.cli.UserInput
import labs.utility.Console
import labs.utility.FileConsole
import labs.utility.Printable
import labs.utility.ScriptManager
import java.util.*
import java.util.function.Predicate

/**
 * Абстрактный класс для формирования объектов классов пользователем.
 * @param <T> Класс формируемого объекта
 * @author dllnnx
</T> */
abstract class Form<T>(console: Printable?) {
    private val console: Printable = if (Console.fileMode) FileConsole() else console!!
    private val scanner: UserInput = if (Console.fileMode) ScriptManager() else ConsoleInput()

    /**
     * Формирует объекта класса
     * @return Объект класса
     */
    abstract fun build(): T

    /**
     * Формирует объект типа String из пользовательского ввода
     * @param type Название поля, для которого формируется объект
     * @param extraInf Дополнительные ограничения на объект
     * @param validator Валидатор для формируемого объекта
     * @param errMessage Сообщение об ошибке
     * @return Сформированная строка
     */
    fun askString(
        type: String,
        extraInf: String,
        validator: Predicate<String?>,
        errMessage: String,
    ): String {
        while (true) {
            try {
                console.println("Введите $type$extraInf: ")
                val input = scanner.nextLine()!!.trim { it <= ' ' }
                require(validator.test(input))
                return input
            } catch (e: IllegalArgumentException) {
                console.printError("$type должно быть типа String!$errMessage")
            }
        }
    }

    /**
     * Формирует объект класса Enum из пользовательского ввода
     * @param values Возможные значения объекта
     * @param enumName Название класса формируемого объекта
     * @param validator Валидатор для формируемого объекта
     * @return Сформированный объект Enum
     */
    fun askEnum(
        values: Array<Enum<*>>,
        enumName: String,
        validator: Predicate<String?>,
    ): Enum<*> {
        while (true) {
            console.println("Возможные $enumName: ")
            for (value in values) {
                console.println(value.toString())
            }
            console.println("Введите $enumName: ")
            val input = scanner.nextLine()!!.trim { it <= ' ' }
            try {
                require(validator.test(input))
                for (value in values) {
                    if (value.toString() == input.uppercase(Locale.getDefault())) {
                        return value
                    }
                }
                console.printError("Такого значения нет в списке! :(( Попробуйте еще раз: ")
            } catch (e: IllegalArgumentException) {
                console.printError("Значение этого поля не может быть null :(( Попробуйте еще раз: ")
            }
        }
    }

    /**
     * Формирует число типа Integer из пользовательского ввода
     * @param type Название поля, для которого формируется объект
     * @param extraInf Дополнительные ограничения на объект
     * @param validator Валидатор для формируемого объекта
     * @param errMessage Сообщение об ошибке
     * @return Сформированное число
     */
    fun askInteger(
        type: String,
        extraInf: String,
        validator: Predicate<Int?>,
        errMessage: String,
    ): Int {
        while (true) {
            console.println("Введите $type$extraInf: ")
            val input = scanner.nextLine()!!.trim { it <= ' ' }
            try {
                val num = input.toInt()
                require(validator.test(num))
                return num
            } catch (e: IllegalArgumentException) {
                console.printError("$type должно быть числом типа Integer!$errMessage")
            }
        }
    }

    /**
     * Формирует число типа float из пользовательского ввода
     * @param type Название поля, для которого формируется объект
     * @param extraInf Дополнительные ограничения на объект
     * @param validator Валидатор для формируемого объекта
     * @param errMessage Сообщение об ошибке
     * @return Сформированное число
     */
    fun askFloat(
        type: String,
        extraInf: String,
        validator: Predicate<Float?>,
        errMessage: String,
    ): Float {
        while (true) {
            console.println("Введите $type$extraInf: ")
            val input = scanner.nextLine()!!.trim { it <= ' ' }
            try {
                val num = input.toFloat()
                require(validator.test(num))
                return num
            } catch (e: IllegalArgumentException) {
                console.printError("$type должна быть числом типа Float!$errMessage")
            }
        }
    }

    /**
     * Формирует число типа double из пользовательского ввода
     * @param type Название поля, для которого формируется объект
     * @param extraInf Дополнительные ограничения на объект
     * @param validator Валидатор для формируемого объекта
     * @param errMessage Сообщение об ошибке
     * @return Сформированное число
     */
    fun askDouble(
        type: String,
        extraInf: String,
        validator: Predicate<Double?>,
        errMessage: String,
    ): Double {
        while (true) {
            console.println("Введите $type$extraInf: ")
            val input = scanner.nextLine()!!.trim { it <= ' ' }
            try {
                val num = input.toDouble()
                require(validator.test(num))
                return num
            } catch (e: IllegalArgumentException) {
                console.printError("$type должна быть числом типа Float!$errMessage")
            }
        }
    }
}

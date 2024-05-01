package labs.cli.forms

import labs.cli.ConsoleInput
import labs.cli.UserInput
import labs.dto.User
import labs.utility.Console
import labs.utility.ConsoleColor
import labs.utility.FileConsole
import labs.utility.Printable
import labs.utility.ScriptManager
import java.util.Objects

class UserForm(console: Printable) : Form<User>(console) {
    private val console: Printable = if (Console.fileMode) FileConsole() else console
    private val scanner: UserInput = if (Console.fileMode) ScriptManager() else ConsoleInput()

    override fun build(): User {
        return User(loginForm(), passwordForm())
    }

    fun askIfLogin(): Boolean {
        while (true) {
            console.print("У Вас уже есть аккаунт? [y/n] ")
            val answer = scanner.nextLine()!!.trim().lowercase()
            when (answer) {
                "y", "yes", "д", "да" -> return true
                "n", "no", "н", "нет" -> return false
                else -> console.printError("Некорректный ответ!")
            }
        }
    }

    private fun loginForm(): String {
        var login: String
        while (true) {
            console.println(ConsoleColor.setConsoleColor("Введите логин: ", ConsoleColor.CYAN))
            login = scanner.nextLine()!!.trim().lowercase()
            if (login.isEmpty()) {
                console.printError("Логин не может быть пустым!")
//                if (Console.fileMode)
            } else {
                return login
            }
        }
    }

    private fun passwordForm(): String {
        var password: String
        while (true) {
            console.println(ConsoleColor.setConsoleColor("Введите пароль: ", ConsoleColor.CYAN))
            password =
                if (Objects.isNull(System.console())) {
                    scanner.nextLine()!!.trim()
                } else {
                    String(System.console().readPassword())
                }
            if (password.isEmpty()) {
                console.printError("Логин не может быть пустым!")
//                if (Console.fileMode)
            } else {
                return password
            }
        }
    }
}

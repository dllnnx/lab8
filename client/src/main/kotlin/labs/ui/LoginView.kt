package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import labs.dto.Request
import labs.dto.ResponseStatus
import labs.dto.User
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.c
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.hyperlink
import tornadofx.imageview
import tornadofx.label
import tornadofx.passwordfield
import tornadofx.textfield

class LoginView : View() {
    val controller: CollectionController by inject()
    val login = SimpleStringProperty()
    val password = SimpleStringProperty()
    val failLogin = SimpleStringProperty()

    override val root =
        form {
            title = "Вход"
            addClass(Styles.form)
            setPrefSize(400.0, 150.0)

            imageview("/user_icon.png") {
                alignment = Pos.CENTER
                fitHeight = 100.0
                fitWidth = 100.0
            }

            fieldset("Для входа введите логин и пароль") {
                alignment = Pos.CENTER
                field("Логин:") {
                    textfield(login)
                }

                field("Пароль:") {
                    passwordfield(password)
                }
            }

            button("Войти") {
                addClass(Styles.button)
                action {
                    if (login.value?.isNotBlank() == true && password.value?.isNotBlank() == true) {
                        val user = User(login.value, password.value)
                        val resp = controller.login(Request("login", "", user))
                        if (resp.status == ResponseStatus.OK) {
                            failLogin.set("")
                            replaceWith<TableView>()
                        } else {
                            failLogin.value = "Неправильный логин или пароль! Попробуйте снова"
                        }
                    } else {
                        failLogin.value = "Введите логин и пароль!"
                    }
                }
            }

            label(failLogin) {
                textFill = c("#D42C2C")
                font = Font.font("Industrial", FontWeight.BOLD, 13.0)
            }

            hyperlink("У меня еще нет аккаунта") {
                addClass(Styles.hyperlink)
                action { replaceWith<RegisterView>() }
            }
        }

    override fun onDock() {
        super.onDock()
        failLogin.set("")
    }
}

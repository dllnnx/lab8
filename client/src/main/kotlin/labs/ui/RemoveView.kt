package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import labs.dto.ResponseStatus
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.c
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.label
import tornadofx.textfield

class RemoveView: View() {
    private val personId = SimpleStringProperty()
    private val controller: CollectionController by inject()
    private val wrongArgs = SimpleStringProperty()

    override val root = form{
        title = "Удаление объекта"
        addClass(Styles.form)
        fieldset("Id объекта") {
            field("Введите id:") {
                textfield(personId)
            }

            button("Удалить"){
                alignment = Pos.CENTER
                action {
                    if (personId.value?.isNotBlank() == true) {
                        val response = controller.removePerson(personId.value.trim())

                        if (response.status == ResponseStatus.WRONG_ARGUMENTS)
                            wrongArgs.set("Проверьте тип аргумента!")
                        else {
                            personId.set("")
                            wrongArgs.set("")
                            close()

                            when (response.status) {
                                ResponseStatus.OK -> {
                                    showAlert(
                                        Alert.AlertType.INFORMATION,
                                        "Успех",
                                        "Удаление элемента с id = ${personId.value} произошло успешно!"
                                    )
                                }

                                ResponseStatus.WARNING -> {
                                    showAlert(Alert.AlertType.WARNING, "Предупреждение", response.message)
                                }

                                else -> {
                                    showAlert(Alert.AlertType.ERROR, "Ошибка при удалении элемента", response.message)
                                }
                            }
                        }
                    }
                    else wrongArgs.set("Введите id")
                }
            }
        }

        label(wrongArgs) {
            textFill = c("#D42C2C")
            font = Font.font("Industrial", FontWeight.BOLD, 13.0)
        }
    }

    private fun showAlert(alertType: Alert.AlertType, title: String, message: String) {
        val alert = Alert(alertType)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }

    override fun onDock() {
        super.onDock()
        wrongArgs.set("")
    }
}
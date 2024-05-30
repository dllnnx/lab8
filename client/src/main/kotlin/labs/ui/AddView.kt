package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
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
import tornadofx.combobox
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.label
import tornadofx.textfield

class AddView : View() {
    private val controller: CollectionController by inject()

    private val name = SimpleStringProperty()
    private val cordX = SimpleStringProperty()
    private val cordY = SimpleStringProperty()
    private val persHeight = SimpleStringProperty()
    private val eyeColor = SimpleStringProperty()
    private val hairColor = SimpleStringProperty()
    private val country = SimpleStringProperty()
    private val locationX = SimpleStringProperty()
    private val locationY = SimpleStringProperty()
    private val locationName = SimpleStringProperty()

    private val wrongArgs = SimpleStringProperty()

    override val root =
        form {
            title = "Добавление объекта"
            addClass(Styles.form)

            fieldset("Данные человека") {
                field("Имя") {
                    textfield(name)
                }

                field("Координата X") {
                    textfield(cordX)
                }

                field("Координата Y") {
                    textfield(cordY)
                }

                field("Рост") {
                    textfield(persHeight)
                }

                field("Цвет глаз") {
                    combobox<String>(eyeColor) {
                        items = FXCollections.observableArrayList("GREEN", "YELLOW", "WHITE")
                    }
                }

                field("Цвет волос") {
                    combobox<String>(hairColor) {
                        items = FXCollections.observableArrayList("GREEN", "BLACK", "ORANGE", "WHITE")
                    }
                }

                field("Страна происхождения") {
                    combobox<String>(country) {
                        items = FXCollections.observableArrayList("RUSSIA", "UNITED_KINGDOM", "ITALY")
                    }
                }

                field("Локация по X") {
                    textfield(locationX)
                }

                field("Локация по Y") {
                    textfield(locationY)
                }

                field("Название локации") {
                    textfield(locationName)
                }

                button("Добавить") {
                    alignment = Pos.CENTER
                    action {
                        if (name.value?.isNotBlank() == true &&
                            cordX.value?.isNotBlank() == true &&
                            cordY.value?.isNotBlank() == true &&
                            persHeight.value?.isNotBlank() == true &&
                            eyeColor.value?.isNotBlank() == true &&
                            hairColor.value?.isNotBlank() == true &&
                            country.value?.isNotBlank() == true &&
                            locationX.value?.isNotBlank() == true &&
                            locationY.value?.isNotBlank() == true &&
                            locationName.value?.isNotBlank() == true
                        ) {
                            val response =
                                controller.addPerson(
                                    name.value.trim(),
                                    cordX.value.trim(),
                                    cordY.value.trim(),
                                    persHeight.value.trim(),
                                    eyeColor.value.trim(),
                                    hairColor.value.trim(),
                                    country.value.trim(),
                                    locationX.value.trim(),
                                    locationY.value.trim(),
                                    locationName.value.trim(),
                                )

                            if (response.status == ResponseStatus.WRONG_ARGUMENTS) {
                                wrongArgs.set("Проверьте тип аргументов!")
                            } else {
                                clearFields()
                                wrongArgs.value = ""
                                close()

                                when (response.status) {
                                    ResponseStatus.OK ->
                                        showAlert(
                                            Alert.AlertType.INFORMATION,
                                            "Успех",
                                            "Объект успешно добавлен в коллекцию!",
                                        )

                                    ResponseStatus.WARNING -> showAlert(Alert.AlertType.WARNING, "Предупреждение", response.message)

                                    else -> showAlert(Alert.AlertType.ERROR, "Ошибка при добавлении элемента", response.message)
                                }
                            }
                        } else {
                            wrongArgs.set("Заполните все поля!")
                        }
                    }
                }
            }

            label(wrongArgs) {
                textFill = c("#D42C2C")
                font = Font.font("Industrial", FontWeight.BOLD, 13.0)
            }
        }

    private fun clearFields() {
        name.set("")
        cordX.set("")
        cordY.set("")
        persHeight.set("")
        eyeColor.set("")
        hairColor.set("")
        country.set("")
        locationX.set("")
        locationY.set("")
        locationName.set("")
    }

    private fun showAlert(
        alertType: Alert.AlertType,
        title: String,
        message: String,
    ) {
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

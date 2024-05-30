package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import labs.dto.ResponseStatus
import labs.objects.Person
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

class UpdateView : View() {
    private val controller: CollectionController by inject()
    val person: Person by param()

    private val name = SimpleStringProperty(person.name)
    private val cordX = SimpleStringProperty(person.coordinates.x.toString())
    private val cordY = SimpleStringProperty(person.coordinates.y.toString())
    private val persHeight = SimpleStringProperty(person.height.toString())
    private val eyeColor = SimpleStringProperty(person.eyeColor.name)
    private val hairColor = SimpleStringProperty(person.hairColor.name)
    private val country = SimpleStringProperty(person.nationality.name)
    private val locationX = SimpleStringProperty(person.location.x.toString())
    private val locationY = SimpleStringProperty(person.location.y.toString())
    private val locationName = SimpleStringProperty(person.location.name)

    private val wrongArgs = SimpleStringProperty()

    override val root =
        form {
            title = "Редактирование объекта"
            addClass(Styles.form)

            fieldset("Редактировать данные человека") {
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

                button("Сохранить") {
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
                                controller.updatePerson(
                                    person.id.toString(),
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
                                wrongArgs.set("Проверьте тип аргументов")
                            } else {
                                clearFields()
                                wrongArgs.set("")
                                close()
                                when (response.status) {
                                    ResponseStatus.OK ->
                                        showAlert(Alert.AlertType.INFORMATION, "Успех", "Объект успешно изменен!")

                                    ResponseStatus.WARNING ->
                                        showAlert(Alert.AlertType.WARNING, "Предупреждение", response.message)

                                    else ->
                                        showAlert(Alert.AlertType.ERROR, "Ошибка при изменении объекта", response.message)
                                }
                            }
                        } else {
                            wrongArgs.set("Все поля должны быть заполнены")
                        }
                    }
                }
            }

            label(wrongArgs) {
                textFill = c("#D42C2C")
                font = Font.font("Industrial", FontWeight.BOLD, 13.0)
            }
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

    override fun onDock() {
        super.onDock()
        wrongArgs.set("")
    }
}

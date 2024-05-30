package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import javafx.scene.text.TextAlignment
import labs.shared.CollectionHolder
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

class SelectIdView : View() {
    private val personId = SimpleStringProperty()
    private val holder: CollectionHolder by inject<CollectionHolder>()
    private val controller: CollectionController by inject<CollectionController>()
    private val wrongArgs = SimpleStringProperty()

    override val root =
        form {
            title = "Редактирование объекта"
            addClass(Styles.form)
            setPrefSize(400.0, 200.0)

            fieldset("Выбор Person") {
                field("Введите id:") {
                    textfield(personId)
                }
            }

            button("Перейти к редактированию") {
                addClass(Styles.button)

                action {
                    if (personId.value?.isNotBlank() == true) {
                        val person = holder.data.find { it.id == personId.value.toLong() }
                        if (person == null) {
                            wrongArgs.set("Нет объекта Person с таким id")
                        } else if (!controller.checkUserIsOwner(person)) {
                            wrongArgs.set("Вы не можете редактировать этот объект, так как он был создан не Вами")
                        } else {
                            find<UpdateView>(mapOf(UpdateView::person to person)).openModal()
                            personId.set("")
                            close()
                        }
                    } else {
                        wrongArgs.set("Введите id")
                    }
                }
            }

            label(wrongArgs) {
                textFill = c("#D42C2C")
                font = Font.font("Industrial", FontWeight.BOLD, 13.0)
                isWrapText = true
                textAlignment = TextAlignment.CENTER
            }
        }

    override fun onDock() {
        super.onDock()
        wrongArgs.set("")
    }
}

package labs.ui

import javafx.beans.property.SimpleStringProperty
import labs.shared.CollectionHolder
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.button
import tornadofx.field
import tornadofx.fieldset
import tornadofx.form
import tornadofx.textfield

class SelectIdView: View() {
    private val personId = SimpleStringProperty()
    private val holder: CollectionHolder by inject<CollectionHolder>()

    override val root = form {
        title = "Редактирование объекта"
        addClass(Styles.form)

        fieldset ("Выбор Person") {
            field("Введите id:") {
                textfield(personId)
            }
        }

        button("Перейти к редактированию") {
            addClass(Styles.button)

            action {
                val person = holder.data.find { it.id == personId.value.toLong()}
                find<UpdateView>(mapOf(UpdateView::person to person)).openModal()
                personId.set("")
                close()
            }
        }
    }
}
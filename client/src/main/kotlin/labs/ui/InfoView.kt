package labs.ui

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import tornadofx.View
import tornadofx.addClass
import tornadofx.label
import tornadofx.vbox

class InfoView: View() {
    private val controller: CollectionController by inject<CollectionController>()
    private val info = SimpleStringProperty()
    init {
        val response = controller.collectionInfo()
        info.set(response)
    }

    override val root = vbox {
        addClass(Styles.form)
        setPrefSize(400.0, 120.0)
        alignment = Pos.CENTER

        title = "Информация о коллекции"

        label(info) {
            alignment = Pos.TOP_CENTER
            font = Font.font("Industrial", FontWeight.BOLD, 13.0)
            padding = Insets(10.0)
        }
    }
}
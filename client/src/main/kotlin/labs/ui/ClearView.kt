package labs.ui

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import labs.dto.ResponseStatus
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.button
import tornadofx.center
import tornadofx.hbox
import tornadofx.label
import tornadofx.top

class ClearView: View() {
    private val controller: CollectionController by inject<CollectionController>()

    override val root = borderpane {
        title = "Очистка коллекции"
        addClass(Styles.form)
        prefHeight = 100.0

        top {
            label {
                padding = Insets(10.0)
                alignment = Pos.TOP_CENTER
                font = Font.font("Industrial", FontWeight.BOLD, 13.0)
                text = "Вы действительно хотите удалить элементы, принадлежащие Вам?"
            }
        }

        center {
            hbox(35) {
                alignment = Pos.CENTER

                button {
                    addClass(Styles.button)
                    text = "Да"
                    action {
                        val response = controller.clearCollection()
                        close()
                        when (response.status) {
                            ResponseStatus.WARNING -> showAlert(
                                Alert.AlertType.WARNING,
                                "Предупреждение",
                                response.message
                            )

                            ResponseStatus.OK -> showAlert(Alert.AlertType.INFORMATION, "Успех", response.message)
                            else -> showAlert(Alert.AlertType.ERROR, "Ошибка при удалении объектов", response.message)
                        }
                    }
                }

                button {
                    addClass(Styles.button)
                    alignment = Pos.BOTTOM_RIGHT
                    text = "Нет"
                    action { close() }
                }
            }
        }
    }

    private fun showAlert(alertType: Alert.AlertType, title: String, message: String) {
        val alert = Alert(alertType)
        alert.title = title
        alert.headerText = null
        alert.contentText = message
        alert.showAndWait()
    }
}
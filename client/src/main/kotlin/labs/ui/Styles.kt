package labs.ui

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.box
import tornadofx.c
import tornadofx.cssclass
import tornadofx.px

class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val loginLabel by cssclass()
        val menuBar by cssclass()
        val menuItem by cssclass()
        val tableView by cssclass()
        val top by cssclass()
        val form by cssclass()
        val hyperlink by cssclass()
        val button by cssclass()
        val tabpane by cssclass()
        val tab by cssclass()
    }

    init {
        val primaryColor = c("#5F9EA0")
        val secondaryColor = c("#A0C0C1")
        val topColor = c("#C2D9DA")
        val textColor = Color.BLACK
        val usernameColor = c("#6A514B")
        val tableColor = c("#E3F1F7")
        val hoverColor = c("#87A0A0")
        val selectedColor = c("#779192")
        val tabHoverColor = c("#C8D1D1")

        root {
            backgroundColor += Color.WHITE
            fontFamily = "Industrial"
        }

        s(heading) {
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
            textFill = primaryColor
            padding = box(10.px)
        }

        s(loginLabel) {
            fontSize = 14.px
            textFill = usernameColor
            padding = box(10.px)
        }

        s(menuBar) {
            backgroundColor += primaryColor
            fontSize = 12.px

            and(hover) {
                backgroundColor += secondaryColor
            }
        }

        s(menuItem) {
            textFill = textColor
            backgroundColor += primaryColor
            fontSize = 12.px

            and(hover) {
                backgroundColor += secondaryColor
                textFill = Color.BLACK
            }
        }

        s(tableView) {
            backgroundColor += tableColor
            prefWidth = 800.px
            prefHeight = 600.px

            columnHeader {
                backgroundColor += primaryColor
                textFill = Color.WHITE
                fontWeight = FontWeight.BOLD
                fontSize = 14.px
            }

            tableCell {
                backgroundColor += tableColor
                textFill = Color.BLACK
                borderColor += box(primaryColor)
                fontSize = 12.px
            }
        }

        s(top) {
            backgroundColor += topColor
        }

        s(form) {
            backgroundColor += secondaryColor
        }

        s(hyperlink) {
            textFill = Color.DARKBLUE
            fontWeight = FontWeight.BOLD
        }

        s(button) {
            backgroundColor += secondaryColor
            textFill = Color.BLACK
            borderRadius += box(7.px)
            borderWidth += box(1.px)
            borderColor += box(c("#254F57"))

            and(hover) {
                backgroundColor += hoverColor
            }
        }

        s(tabPane) {
            backgroundColor += secondaryColor
            fontSize = 12.px
        }

        s(tab) {
            backgroundColor += secondaryColor

            and(selected) {
                backgroundColor += selectedColor
            }

            and(hover) {
                backgroundColor += tabHoverColor
            }
        }
    }
}

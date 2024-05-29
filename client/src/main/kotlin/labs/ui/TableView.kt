package labs.ui

import javafx.geometry.Pos
import javafx.scene.control.Alert
import labs.objects.Person
import labs.shared.CollectionHolder
import labs.utility.Client
import org.controlsfx.control.table.TableFilter
import tornadofx.View
import tornadofx.tableview
import tornadofx.value
import tornadofx.borderpane
import tornadofx.center
import tornadofx.top
import tornadofx.menubar
import tornadofx.menu
import tornadofx.item
import tornadofx.action
import tornadofx.addClass
import tornadofx.borderpaneConstraints
import tornadofx.button
import tornadofx.column
import tornadofx.contextmenu
import tornadofx.hbox
import tornadofx.imageview
import tornadofx.label
import tornadofx.left
import tornadofx.onUserSelect
import tornadofx.paddingRight
import tornadofx.paddingTop
import tornadofx.px
import tornadofx.right
import tornadofx.selectedItem
import tornadofx.style
import java.time.format.DateTimeFormatter

class TableView: View() {
    private val holder = CollectionHolder()
    private val client: Client by inject<Client>()

    override val root = borderpane {
        addClass(Styles.heading)

        center {
            tableview(holder.data) {
                addClass(Styles.tableView)

                primaryStage.isMaximized = true
                title = "Обзор коллекции"
                TableFilter.forTableView(this).apply()

                column("id", Person::id)
                column("name", Person::name)
                column("coordinates_x", Person::coordinates).value { it.value.coordinates.x }
                column("coordinates_y", Person::coordinates).value { it.value.coordinates.y }
                column(
                    "creation_date",
                    Person::creationDate
                ).value { it.value.creationDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss z")) }
                column("height", Person::height)
                column("eye_color", Person::eyeColor)
                column("hair_color", Person::hairColor)
                column("nationality", Person::nationality)
                column("location_x", Person::location).value { it.value.location.x }
                column("location_y", Person::location).value { it.value.location.y }
                column("location_name", Person::location).value { it.value.location.name }
                column("creator_login", Person::creatorLogin)
            }
        }

        top {
            addClass(Styles.top)

            borderpane {
                left {
                    menubar {
                        addClass(Styles.menuBar)

                        menu("Изменить коллекцию") {
                            item("Добавить Person") {
                                addClass(Styles.menuItem)
                                action {
                                    find(AddView::class).openModal()
                                }
                            }
                            item("Изменить Person по id") {
                                addClass(Styles.menuItem)
                                action {
                                    find(SelectIdView::class).openModal()
                                }
                            }

                            item("Удалить Person по id") {
                                addClass(Styles.menuItem)
                                action {
                                    find(RemoveView::class).openModal()
                                }
                            }

                            item("Очистить коллекцию") {
                                addClass(Styles.menuItem)
                                action {
                                    find(ClearView::class).openModal()
                                }
                            }
                        }

                        menu("Информация") {
                            item("Узнать информацию о коллекции") {
                                addClass(Styles.menuItem)
                                action {
                                    find(InfoView::class).openModal()
                                }
                            }
                        }
                    }
                }

                right {
                    hbox {
                        imageview("/user_icon.png") {
                            fitHeight = 35.0
                            fitWidth = 35.0
                        }
                        label(client.user.login) {
                            addClass(Styles.loginLabel)
                        }
                    }
                }
            }
        }
    }
}
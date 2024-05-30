package labs.ui

import javafx.animation.TranslateTransition
import javafx.scene.layout.Pane
import javafx.util.Duration
import labs.objects.Person
import labs.shared.CollectionHolder
import labs.utility.Client
import org.controlsfx.control.table.TableFilter
import tornadofx.View
import tornadofx.action
import tornadofx.addClass
import tornadofx.borderpane
import tornadofx.center
import tornadofx.column
import tornadofx.hbox
import tornadofx.imageview
import tornadofx.item
import tornadofx.label
import tornadofx.left
import tornadofx.menu
import tornadofx.menubar
import tornadofx.right
import tornadofx.stackpane
import tornadofx.tab
import tornadofx.tableview
import tornadofx.tabpane
import tornadofx.top
import tornadofx.value
import java.time.format.DateTimeFormatter

class TableView : View() {
    private val holder = CollectionHolder()
    private val client: Client by inject<Client>()

    override val root =
        borderpane {
            addClass(Styles.heading)

            center {
                tabpane {
                    addClass(Styles.tabpane)
                    tab("Таблица") {
                        isClosable = false

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
                                Person::creationDate,
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

                    tab("Визуализация") {
                        isClosable = false

                        stackpane {
                            imageview("/savanna3.jpg") {
                                fitWidthProperty().bind(this@stackpane.widthProperty())
                                fitHeightProperty().bind(this@stackpane.heightProperty())
                            }

                            val visualContainer = Pane()
                            children.add(visualContainer)

                            selectedProperty().addListener { _, _, isSelected ->
                                if (isSelected) {
                                    visualContainer.children.clear()
                                    val persons = holder.data
                                    val centerX = this@stackpane.width / 2
                                    val centerY = this@stackpane.height / 2
                                    persons.forEach { person ->
                                        val image =
                                            imageview("/real_hippo.png") {
                                                fitWidth = 0.35 * person.height
                                                fitHeight = 0.35 * person.height

                                                translateX = centerX - (fitWidth / 2)
                                                translateY = centerY - (fitHeight / 2)
                                            }
                                        val imageName =
                                            label(person.name) {
                                                translateX = centerX
                                                translateY = centerY
                                            }

                                        val animateImage =
                                            TranslateTransition(Duration.seconds(3.0), image).apply {
                                                toX = centerX - person.coordinates.x.toDouble() * 15 + 200
                                                toY = centerY - person.coordinates.y * 5 + 80
                                            }

                                        val animateLabel =
                                            TranslateTransition(Duration.seconds(3.0), imageName).apply {
                                                toX = centerX - person.coordinates.x.toDouble() * 15 + 200
                                                toY = centerY - person.coordinates.y * 5 + 80
                                            }

                                        animateImage.play()
                                        animateLabel.play()

                                        visualContainer.children.addAll(image, imageName)
                                    }
                                }
                            }
                        }
                    }
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

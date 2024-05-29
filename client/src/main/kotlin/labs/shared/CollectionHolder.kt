package labs.shared

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import labs.dto.Request
import labs.objects.Person
import labs.utility.Client
import tornadofx.Controller
import tornadofx.asObservable
import kotlin.concurrent.timer

class CollectionHolder: Controller() {
    private val client: Client by inject<Client>()
    var data: ObservableList<Person> = FXCollections.observableArrayList()

    init {
        updateData()
        timer("collectionUpdateTimer", period = 500, initialDelay = 1000, daemon = true) {
            updateData()
        }
    }

    private fun updateData(): ObservableList<Person> {
        val newData = client
            .sendAndReceiveResponse(Request("show", client.user))
            .collection!!.asObservable()
        data.setAll(newData)
        return data
    }
}
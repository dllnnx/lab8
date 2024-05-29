package labs.ui

import labs.dto.Request
import labs.dto.Response
import labs.dto.ResponseStatus
import labs.objects.Coordinates
import labs.objects.Country
import labs.objects.EyeColor
import labs.objects.HairColor
import labs.objects.Location
import labs.objects.Person
import labs.shared.CollectionHolder
import labs.utility.Client
import tornadofx.Controller
import java.time.ZonedDateTime

class CollectionController : Controller() {
    private val client: Client by inject<Client>()

    fun login(request: Request): Response {
        val response = client.sendAndReceiveResponse(request)
        if (response.status == ResponseStatus.OK) client.user = request.user
        return response
    }

    fun addPerson(name: String, cordX: String, cordY: String, persHeight: String, eyeColor: String, hairColor: String,
                  country: String, locationX: String, locationY: String, locationName: String): Response {
        try {
            val person = Person(
                name, Coordinates(cordX.toFloat(), cordY.toDouble()), ZonedDateTime.now(), persHeight.toInt(),
                EyeColor.valueOf(eyeColor), HairColor.valueOf(hairColor), Country.valueOf(country),
                Location(locationX.toFloat(), locationY.toFloat(), locationName)
            )
            person.creatorLogin = client.user.login
            val response = client.sendAndReceiveResponse(Request("add", "", person, client.user))
            return response
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Проверьте правильность типа аргументов")
        }
    }

    fun updatePerson(id: String, name: String, cordX: String, cordY: String, persHeight: String, eyeColor: String, hairColor: String,
                     country: String, locationX: String, locationY: String, locationName: String) : Response {
        try {
            val person = Person(
                name, Coordinates(cordX.toFloat(), cordY.toDouble()), ZonedDateTime.now(), persHeight.toInt(),
                EyeColor.valueOf(eyeColor), HairColor.valueOf(hairColor), Country.valueOf(country),
                Location(locationX.toFloat(), locationY.toFloat(), locationName)
            )
            val response = client.sendAndReceiveResponse(Request("update", id, person, client.user))
            return response
        } catch (e: IllegalArgumentException) {
            return Response(ResponseStatus.WRONG_ARGUMENTS, "Проверьте правильность типа аргументов")
        }
    }

    fun removePerson(id: String): Response {
        val response = client.sendAndReceiveResponse(Request("remove_by_id", id, client.user))
        return response
    }

    fun clearCollection(): Response {
        val response = client.sendAndReceiveResponse(Request("clear", client.user))
        return response
    }

    fun collectionInfo(): String {
        val response = client.sendAndReceiveResponse(Request("info", client.user))
        return response.message
    }
}
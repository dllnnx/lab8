package labs.objects

import java.io.Serializable
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Класс человека.
 * @author dllnnx
 */

class Person(
    var name: String,
    var coordinates: Coordinates,
    private var creationDate: ZonedDateTime,
    var height: Int,
    private var eyeColor: EyeColor,
    private var hairColor: HairColor,
    var nationality: Country,
    var location: Location,
) : Comparable<Person?>, Serializable {
    var id: Long = 0

    constructor(
        id: Long,
        name: String,
        coordinates: Coordinates,
        creationDate: ZonedDateTime,
        height: Int,
        eyeColor: EyeColor,
        hairColor: HairColor,
        nationality: Country,
        location: Location) : this(name, coordinates, creationDate, height, eyeColor, hairColor, nationality, location) {
        this.id = id
    }

    override fun compareTo(other: Person?): Int {
        return name.compareTo(other?.name!!)
    }

    override fun toString(): String {
        return "Person {\n" +
            "id = $id,\n" +
            "name = $name,\n" +
            "coordinates = $coordinates,\n" +
            "creation_date = ${creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"))},\n" +
            "height = $height,\n" +
            "eye_color = $eyeColor,\n" +
            "hair_color = $hairColor,\n" +
            "nationality = $nationality,\n" +
            "location = $location\n" +
            "}".trimIndent()
    }
}

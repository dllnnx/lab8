package labs.shared.objects

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

/**
 * Класс человека.
 * @author dllnnx
 */

class Person (
    var id: Long,
    var name: String,
    var coordinates: Coordinates,
    private var creationDate: ZonedDateTime,
    var height: Int,
    private var eyeColor: EyeColor,
    private var hairColor: HairColor,
    var nationality: Country,
    var location: Location
) : Comparable<Person?>{

    override fun compareTo(other: Person?): Int {
        return name.compareTo(other?.name!!)
    }

    override fun toString(): String {
        return """
            Person {
            id = $id,
            name = $name,
            coordinates = $coordinates,
            creation_date = ${creationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss"))},
            height = $height,
            eye_color = $eyeColor,
            hair_color = $hairColor,
            nationality = $nationality,
            location = $location
            }
            """.trimIndent()
    }

}
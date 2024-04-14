package labs.cli.forms

import labs.objects.Coordinates
import labs.objects.Country
import labs.objects.EyeColor
import labs.objects.HairColor
import labs.objects.Location
import labs.objects.Person
import labs.utility.Console
import labs.utility.FileConsole
import labs.utility.Printable
import java.time.ZonedDateTime
import java.util.Objects

/**
 * Класс для формирования объектов типа [Person].
 * @author dllnnx
 */
class PersonForm(console: Printable?) : Form<Person?>(console) {
    private val console: Printable = if (Console.fileMode) FileConsole() else console!!

    /**
     * Собирает новый объект класса [Person]
     * @return Объект класса [Person]
     */
    override fun build(): Person {
        return Person(
            askString(
                "имя",
                "",
                { s: String? -> !s.isNullOrBlank() },
                " Имя не может быть пустым",
            ),
            askCoordinates(),
            ZonedDateTime.now(),
            askInteger(
                "рост",
                ". Значение поля должно быть больше 0",
                { s: Int? -> s != null && s > 0 },
                " Минимальное значение поля: 1.",
            ),
            askEyeColor(),
            askHairColor(),
            askCountry(),
            askLocation(),
        )
    }

    private fun askCoordinates(): Coordinates {
        return CoordinatesForm(console).build()
    }

    private fun askEyeColor(): EyeColor {
        return askEnum(
            EyeColor.entries.toTypedArray(),
            "цвета глаз",
        ) { obj: String? -> Objects.nonNull(obj) } as EyeColor
    }

    private fun askHairColor(): HairColor {
        return askEnum(
            HairColor.entries.toTypedArray(),
            "цвета волос",
        ) { obj: String? -> Objects.nonNull(obj) } as HairColor
    }

    private fun askCountry(): Country {
        return askEnum(
            Country.entries.toTypedArray(),
            "страны происхождения",
        ) { obj: String? -> Objects.nonNull(obj) } as Country
    }

    private fun askLocation(): Location {
        return LocationForm(console).build()
    }
}

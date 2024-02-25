package objects.forms

import commandManagement.Console
import commandManagement.FileConsole
import commandManagement.Printable
import managers.CollectionManager
import objects.*
import java.time.ZonedDateTime
import java.util.*

/**
 * Класс для формирования объектов типа [Person].
 * @author dllnnx
 */
class PersonForm (console: Printable?, var collectionManager: CollectionManager) : Form<Person?>(console) {
    private val console: Printable

    init {
        this.console = if (Console.fileMode) FileConsole() else console!!
    }

    /**
     * Собирает новый объект класса [Person]
     * @return Объект класса [Person]
     */
    override fun build(): Person {
        return Person(
            collectionManager.getFreeId(),
            askString(
                "имя", "", { s: String? -> !s.isNullOrBlank() },
                " Имя не может быть пустым"
            ),
            askCoordinates(),
            ZonedDateTime.now(),
            askInteger("рост", ". Значение поля должно быть больше 0",
                { s: Int? -> s != null && s > 0 }, " Минимальное значение поля: 1."
            ),
            askEyeColor(),
            askHairColor(),
            askCountry(),
            askLocation()
        )
    }

    fun build(id: Long): Person {
        return Person(
            id,
            askString(
                "имя", "", { s: String? -> !s.isNullOrBlank() },
                " Имя не может быть пустым(("
            ),
            askCoordinates(),
            ZonedDateTime.now(),
            askInteger("рост", ". Значение поля должно быть больше 0",
                { s: Int? -> s != null && s > 0 }, " Минимальное значение поля: 1."
            ),
            askEyeColor(),
            askHairColor(),
            askCountry(),
            askLocation()
        )
    }

    private fun askCoordinates(): Coordinates {
        return CoordinatesForm(console).build()
    }

    private fun askEyeColor(): EyeColor {
        return askEnum(
            EyeColor.entries.toTypedArray(), "цвета глаз"
        ) { obj: String? -> Objects.nonNull(obj) } as EyeColor
    }

    private fun askHairColor(): HairColor {
        return askEnum(
            HairColor.entries.toTypedArray(), "цвета волос"
        ) { obj: String? -> Objects.nonNull(obj) } as HairColor
    }

    private fun askCountry(): Country {
        return askEnum(
            Country.entries.toTypedArray(), "страны происхождения"
        ) { obj: String? -> Objects.nonNull(obj) } as Country
    }

    private fun askLocation(): Location {
        return LocationForm(console).build()
    }
}

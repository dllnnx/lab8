package labs.cli.forms

import labs.objects.Coordinates
import labs.utility.Printable

/**
 * Класс для формирования объекта класса [Coordinates].
 * @author dllnnx
 */
class CoordinatesForm(console: Printable?) : Form<Coordinates?>(console) {
    /**
     * Собирает новый объект класса [Coordinates]
     * @return Объект класса [Coordinates]
     */
    override fun build(): Coordinates {
        return Coordinates(
            askFloat(
                "координата X",
                ". Значение поля не должно превышать 737",
                { s: Float? -> s != null && s <= 737 },
                " Максимально допустимое значение поля: 737.",
            ),
            askDouble(
                "координата Y",
                ". Значение поля не должно превышать 113",
                { s: Double? -> s != null && s <= 113 },
                " Максимально допустимое значение поля: 113.",
            ),
        )
    }
}

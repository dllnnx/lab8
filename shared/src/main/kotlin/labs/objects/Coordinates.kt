package labs.objects

import java.io.Serializable

/**
 * Класс координат.
 * @author dllnnx
 */
class Coordinates(
    var x: Float,
    var y: Double,
) : Serializable {
    override fun toString(): String {
        return "($x, $y)"
    }
}

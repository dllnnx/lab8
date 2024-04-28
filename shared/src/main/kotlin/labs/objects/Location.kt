package labs.objects

import java.io.Serializable

/**
 * Класс локации.
 * @author dllnnx
 */
class Location(var x: Float, var y: Float, var name: String?) : Serializable {
    override fun toString(): String {
        return "(" +
            "name = " + name + ", " +
            "x = " + x + ", " +
            "y = " + y +
            ")"
    }
}

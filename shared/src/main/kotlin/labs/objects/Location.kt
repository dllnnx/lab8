package labs.objects

import java.io.Serializable

/**
 * Класс локации.
 * @author dllnnx
 */
class Location(private var x: Float, private var y: Float, var name: String?): Serializable {
    override fun toString(): String {
        return "(" +
            "name = " + name + ", " +
            "x = " + x + ", " +
            "y = " + y +
            ")"
    }
}

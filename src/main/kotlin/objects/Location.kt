package objects

/**
 * Класс локации.
 * @author dllnnx
 */
class Location (private var x: Float, private var y: Float, var name: String?){

    override fun toString(): String {
        return "Location {" +
                "name = " + name + ", " +
                "x = " + x + ", " +
                "y = " + y +
                "}"
    }
}

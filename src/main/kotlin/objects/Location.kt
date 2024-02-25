package objects

/**
 * Класс локации.
 * @author dllnnx
 */
class Location (var x: Float, var y: Float, var name: String){

    override fun toString(): String {
        return "Location {" +
                "name = " + name + ", " +
                "x = " + x + ", " +
                "y = " + y +
                "}"
    }
}

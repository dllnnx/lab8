package objects

import lombok.AllArgsConstructor
import lombok.Getter

/**
 * Класс локации.
 * @author dllnnx
 */
@Getter
class Location constructor(var x: Float, var y: Float, var name: String){

    override fun toString(): String {
        return "Location {" +
                "name = " + name + ", " +
                "x = " + x + ", " +
                "y = " + y +
                "}"
    }
}

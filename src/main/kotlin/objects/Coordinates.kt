package objects

import lombok.AllArgsConstructor
import lombok.Getter
import kotlin.properties.Delegates

/**
 * Класс координат.
 * @author dllnnx
 */
@Getter
@AllArgsConstructor
class Coordinates constructor(
    var x: Float,//Максимальное значение поля: 737
    var y: Double //Максимальное значение поля: 113
){

    override fun toString(): String {
        return "($x, $y)"
    }
}
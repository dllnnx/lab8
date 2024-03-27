package labs.objects

import java.io.Serializable


/**
 * Класс координат.
 * @author dllnnx
 */
class Coordinates (
    var x: Float,//Максимальное значение поля: 737
    var y: Double //Максимальное значение поля: 113
) : Serializable{

    override fun toString(): String {
        return "($x, $y)"
    }
}
package objects


/**
 * Класс координат.
 * @author dllnnx
 */
class Coordinates (
    var x: Float,//Максимальное значение поля: 737
    var y: Double //Максимальное значение поля: 113
){

    override fun toString(): String {
        return "($x, $y)"
    }
}
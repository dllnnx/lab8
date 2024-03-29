package labs.objects

import java.io.Serializable

/**
 * Перечисление стран происхождения человека.
 * @author dllnnx
 */
enum class Country(val thousandsOfArea: Int) : Serializable {
    RUSSIA(17000),
    UNITED_KINGDOM(243),
    ITALY(302),
}

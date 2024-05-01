package labs.dto

import java.io.Serializable

@JvmRecord
data class User (val login: String, val password: String): Serializable {
    override fun toString(): String {
        return "$login : $password"
    }
}
package labs.commands

import java.util.Objects

/**
 * Абстрактный класс для всех команд
 * @author dllnnx
 */
abstract class Command(var name: String, var description: String) : CommandInterface {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val command = other as Command
        return name == command.name && description == command.description
    }

    override fun hashCode(): Int {
        return Objects.hash(name, description)
    }

    override fun toString(): String {
        return name + description
    }
}

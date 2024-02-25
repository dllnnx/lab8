package exceptions

/**
 * Класс исключения. Выбрасывается, если объект с указанным id не найден.
 * @author dllnnx
 */
class NoSuchIdException : RuntimeException() {
    override fun toString(): String {
        return "Нет элемента с таким id в коллекции!"
    }
}

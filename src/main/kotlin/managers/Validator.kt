package managers

import objects.Coordinates
import objects.Location
import objects.Person

/**
 * Валидатор объектов класса Person.
 * @author dllnnx
 */
class Validator {
    /**
     * Проверяет экземпляр класса Coordinates на валидность
     * @param coordinates Проверяемый экземпляр
     * @return true, если объект валидный, иначе false
     */
    private fun validateCoordinates(coordinates: Coordinates): Boolean {
        return coordinates.x <= 737 && coordinates.y <= 113
    }

    /**
     * Проверяет экземпляр класса Location на валидность
     * @param location Проверяемый экземпляр
     * @return true, если объект валидный, иначе false
     */
    private fun validateLocation(location: Location): Boolean {
        return location.x != null && location.y != null && (location.name == null || location.name.length <= 889)
    }

    /**
     * Проверяет экземпляр класса Person на валидность
     * @param person Проверяемый экземпляр
     * @return true, если объект валидный, иначе false
     */
    fun validatePerson(person: Person): Boolean {
        return person.name != null && person.name.isNotBlank()
                && validateCoordinates(person.coordinates)
                && person.creationDate != null
                && person.height != null && person.height > 0
                && person.eyeColor != null
                && person.hairColor != null
                && person.nationality != null
                && validateLocation(person.location)
    }
}

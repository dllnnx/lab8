package labs.utility

import labs.objects.Coordinates
import labs.objects.Location
import labs.objects.Person

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
        return location.name == null || location.name?.length!! <= 889
    }

    /**
     * Проверяет экземпляр класса Person на валидность
     * @param person Проверяемый экземпляр
     * @return true, если объект валидный, иначе false
     */

    fun validatePerson(person: Person): Boolean {
        return person.name.isNotBlank() &&
            validateCoordinates(person.coordinates) &&
            person.height > 0 &&
            validateLocation(person.location)
    }
}

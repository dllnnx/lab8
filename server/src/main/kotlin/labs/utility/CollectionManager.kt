package labs.utility

import labs.database.DatabaseConnector
import labs.objects.Person
import java.util.Date
import java.util.LinkedList

/**
 * Менеджер коллекции.
 * @author dllnnx
 */
class CollectionManager {
    val initializationTime = Date()
    var collection: LinkedList<Person?> = LinkedList()

    init {
        collection.addAll(DatabaseConnector.databaseManager.fillCollection())
    }

    fun getCollectionType(): String {
        return collection.javaClass.name
    }

    fun getCollectionSize(): Int {
        return collection.size
    }

    fun addElement(person: Person?) {
        collection.add(person)
    }

    fun updateById(
        person: Person?,
        id: Long,
    ) {
        removeById(id)
        person?.id = id
        collection.add(person)
    }

    fun getById(id: Long): Person? {
        return collection.filter { it!!.id == id }.getOrElse(0) { null }
    }

    fun checkExistById(id: Long): Boolean {
        return collection.any { it!!.id == id }
    }

    fun removeById(id: Long): Boolean {
        if (getById(id) != null) {
            collection.remove(getById(id))
            return true
        } else {
            return false
        }
    }

    fun removeElements(ids: List<Long>) {
        ids.forEach { it -> collection.remove(this.getById(it)) }
    }

    fun getByHeight(height: Int): LinkedList<Person?> {
        return collection.filter { it?.height == height }.toCollection(LinkedList<Person?>())
    }

    fun filterContainsName(name: String?): LinkedList<Person?> {
        return collection.filter { it?.name?.contains(name.toString()) == true }.toCollection(LinkedList<Person?>())
    }

    fun maxByNationality(): Person? {
        return collection.maxByOrNull { it!!.nationality.thousandsOfArea }
    }

    fun shuffle() {
        collection.shuffle()
    }
}

package labs.utility

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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
    private val mutex = Mutex()

    init {
        collection.addAll(DatabaseConnector.databaseManager.fillCollection())
    }

    suspend fun getCollectionType(): String {
        mutex.withLock {
            return collection.javaClass.name
        }
    }

    suspend fun getCollectionSize(): Int {
        mutex.withLock {
            return collection.size
        }
    }

    suspend fun addElement(person: Person?) {
        mutex.withLock {
            collection.add(person)
        }
    }

    suspend fun updateById(
        person: Person?,
        id: Long,
    ) {
        mutex.withLock {
            removeById(id)
            person?.id = id
            collection.add(person)
        }
    }

    suspend fun getById(id: Long): Person? {
        mutex.withLock {
            return collection.filter { it!!.id == id }.getOrElse(0) { null }
        }
    }

    suspend fun checkExistById(id: Long): Boolean {
        mutex.withLock {
            return collection.any { it!!.id == id }
        }
    }

    suspend fun removeById(id: Long): Boolean {
        mutex.withLock {
            if (getById(id) != null) {
                collection.remove(getById(id))
                return true
            } else {
                return false
            }
        }
    }

    suspend fun removeElements(ids: List<Long>) {
        mutex.withLock {
            ids.forEach { it -> collection.remove(this.getById(it)) }
        }
    }

    suspend fun getByHeight(height: Int): LinkedList<Person?> {
        mutex.withLock {
            return collection.filter { it?.height == height }.toCollection(LinkedList<Person?>())
        }
    }

    suspend fun filterContainsName(name: String?): LinkedList<Person?> {
        mutex.withLock {
            return collection.filter { it?.name?.contains(name.toString()) == true }.toCollection(LinkedList<Person?>())
        }
    }

    suspend fun maxByNationality(): Person? {
        mutex.withLock {
            return collection.maxByOrNull { it!!.nationality.thousandsOfArea }
        }
    }

    suspend fun shuffle() {
        mutex.withLock {
            collection.shuffle()
        }
    }
}

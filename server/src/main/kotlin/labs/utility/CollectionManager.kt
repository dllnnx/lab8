package labs.utility

import labs.objects.Person
import org.apache.logging.log4j.kotlin.logger
import java.util.Date
import java.util.LinkedList

/**
 * Менеджер коллекции.
 * @author dllnnx
 */
class CollectionManager {
    /**
     * Дата инициализации коллекции
     */
    val initializationTime = Date()
    var collection: LinkedList<Person?> = LinkedList()
    private val logger = logger()

    /**
     * Возвращает имя типа коллекции
     * @return Имя типа коллекции
     */
    fun getCollectionType(): String {
        return collection.javaClass.name
    }

    /**
     * Возвращает размер коллекции
     * @return Размер коллекции
     */
    fun getCollectionSize(): Int {
        return collection.size
    }

    /**
     * Добавляет элемент в коллекцию
     * @param person Элемент для добавления.
     */
    fun addElement(person: Person?) {
        person?.id = getFreeId()
        collection.add(person)
    }

    fun updateById(person: Person?, id: Long){
        removeById(id)
        person?.id = id
        collection.add(person)
    }

    /**
     * Получает элемент коллекции по заданному значению id
     * @param id id элемента
     * @return Элемент по заданному значению id или null, если не найдено
     */
    fun getById(id: Long): Person? {
        return collection.filter { it!!.id == id }.getOrElse(0) { null }
    }

    /**
     * Удаляет элемент коллекции по заданному значению id
     * @param id id элемента
     */
    fun removeById(id: Long): Boolean {
        if (getById(id) != null) {
            collection.remove(getById(id))
            return true
        } else {
            return false
        }
    }

    /**
     * Очищает коллекцию
     */
    fun clearCollection() {
        collection.clear()
        logger.info("Коллекция очищена")
    }

    /**
     * Удаляет первый элемент коллекции
     */
    fun removeFirst() {
        val firstPerson: Person = collection.toTypedArray()[0] as Person
        collection.remove(firstPerson)
    }

    /**
     * Находит элементы коллекции по заданному значению поля height
     * @param height height элементов
     * @return List из найденных элементов
     */
    fun getByHeight(height: Int): LinkedList<Person?> {
        return collection.filter { it?.height == height }.toCollection(LinkedList<Person?>())
    }

    /**
     * Находит элементы коллекции, значение поля name которых содержит заданную подстроку
     * @param name Заданная подстрока
     * @return List из найденных элементов
     */
    fun filterContainsName(name: String?): LinkedList<Person?> {
        return collection.filter { it?.name?.contains(name.toString()) == true }.toCollection(LinkedList<Person?>())
    }

    /**
     * Находит любой элемент коллекции, значение поля nationality которого является максимальным
     * @return любой элемент коллекции, значение поля nationality которого является максимальным
     */
    fun maxByNationality(): Person? {
        return collection.maxByOrNull { it!!.nationality.thousandsOfArea }
    }

    /**
     * Перемешивает коллекцию.
     */
    fun shuffle() {
        collection.shuffle()
    }

    fun getFreeId(): Long {
        if (collection.isEmpty()) return 0
        val ids = collection.map { it!!.id }.toMutableList()
        for (i in 0..collection.maxOf { it!!.id }) {
            if (!ids.contains(i)) return i
        }
        return collection.maxOf { it!!.id } + 1
    }
}

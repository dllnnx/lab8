package labs.server.utility

import com.google.gson.stream.JsonWriter
import shared.utility.Console
import shared.utility.ConsoleColor
import client.cli.forms.Validator
import shared.objects.Person
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.time.ZonedDateTime
import java.util.*

/**
 * Менеджер для работы с файлами.
 * @author dllnnx
 */
class FileManager (private var console: Console, private var collectionManager: CollectionManager){
    private val gson: com.google.gson.Gson = com.google.gson.GsonBuilder()
        .setPrettyPrinting()
        .registerTypeAdapter(ZonedDateTime::class.java, object : com.google.gson.TypeAdapter<ZonedDateTime?>() {
            @Throws(IOException::class)
            override fun write(out: JsonWriter?, value: ZonedDateTime?) {
                out?.value(value.toString())
            }

            @Throws(IOException::class)
            override fun read(`in`: com.google.gson.stream.JsonReader): ZonedDateTime {
                return ZonedDateTime.parse(`in`.nextString())
            }
        })
        .create()


    /**
     * Сохраняет коллекцию в json файл.
     */
    fun saveObjects() {
        val filePath = System.getProperty("file_path")
        if (filePath == null || filePath.isEmpty()) {
            console.printError("Путь к файлу должен находиться в переменной окружения file_path! :((")
            return
        }
        try {
            val out = FileOutputStream(filePath)
            out.write(gson.toJson(collectionManager.collection).toByteArray())
            out.close()
            console.println(
                ConsoleColor.setConsoleColor("Ура, сохранение данных произошло успешно!!",
                ConsoleColor.GREEN))
        } catch (e: FileNotFoundException) {
            console.printError("Такого файла не существует(((")
        } catch (e: IOException) {
            console.printError("Ой, при сохранении данных в файл произошла ошибка( Проверьте данные!")
        }
    }

    /**
     * Заполняет коллекцию при запуске программы.
     */
    fun fillCollection() {
        val filePath = System.getProperty("file_path")
        if (filePath == null || filePath.isEmpty()) {
            console.printError("Путь к исходному файлу должен находиться в переменной окружения file_path! :((")
        }
        try {
            val jsonText = StringBuilder()
            val scanner = Scanner(File(filePath))
            while (scanner.hasNext()) {
                jsonText.append(scanner.nextLine())
            }
            val people: Array<Person> = gson.fromJson(
                jsonText.toString(),
                Array<Person>::class.java
            )
            val validator = Validator()

            for (person in people) {
                if (validator.validatePerson(person)) {
                    collectionManager.addElement(person)
                } else {
                    console.printError("Не все данные валидны! В коллекцию добавлены только валидные объекты.")
                }
            }
        } catch (e: FileNotFoundException) {
            console.printError("Такого файла не существует :(")
        } catch (e: IllegalArgumentException) {
            console.printError("Данные в файле невалидны! Коллекция не заполнена :((")
        } catch (e: NullPointerException){
            console.printError("Данные в файле невалидны! Коллекция не заполнена :((")
        }
    }
}

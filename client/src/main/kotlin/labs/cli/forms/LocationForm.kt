package labs.client.cli.forms

import shared.utility.Printable
import shared.objects.Location
import java.util.*

class LocationForm(console: Printable?) : Form<Location?>(console) {
    override fun build(): Location {
        return Location(
            askFloat("координата X", "",
                { obj: Float? -> Objects.nonNull(obj) }, ""
            ),
            askFloat("координата Y", "",
                { obj: Float? -> Objects.nonNull(obj) }, ""
            ),
            askString(
                "название локации", ". Длина строки не должна превышать 889",
                { s: String? -> s.toString().length <= 889 },
                " Длина строки не может быть больше 889(("
            )
        )
    }
}

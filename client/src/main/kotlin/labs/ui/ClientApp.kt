package labs.ui

import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.App

class ClientApp: App(LoginView::class, Styles::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        stage.icons.add(Image(resources.stream("/hippo.png")))
    }
}
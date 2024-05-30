plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "labs"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.google.code.gson:gson:2.9.1")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.openjfx:javafx-controls:15.0.1")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

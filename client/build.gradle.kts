plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    application
}

group = "labs"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":shared")))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClass.set("labs.Main")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Jar>("fatJar") {
    dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources")) // We need this for Gradle optimization to work
    archiveClassifier.set("app") // Naming the jar
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest { attributes(mapOf("Main-Class" to application.mainClass)) } // Provided we set it up in the application plugin configuration
    val sourcesMain = sourceSets.main.get()
    val contents =
        configurations
            .runtimeClasspath
            .get()
            .map { if (it.isDirectory) it else zipTree(it) } +
            sourcesMain.output
    from(contents)
}

kotlin {
    jvmToolchain(17)
}

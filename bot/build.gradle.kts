import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.shadow.jar)
    application
}

dependencies {
    implementation(libs.telegram.api)
    implementation(libs.kotlinx.serialization)
    implementation("org.litote.kmongo:kmongo:4.10.0")
    implementation("io.insert-koin:koin-core:3.4.0")
}

application {
    mainClass.set("com.sliderzxc.everyonebottelegram.application.EveryoneBotApplicationKt")
}


tasks.withType<ShadowJar> {
    archiveBaseName.set("application")
    archiveClassifier.set("")
}

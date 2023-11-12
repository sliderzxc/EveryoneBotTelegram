plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    implementation(libs.telegram.api)
    implementation(libs.kotlinx.serialization)
    implementation("org.litote.kmongo:kmongo:4.10.0")
    implementation("io.insert-koin:koin-core:3.4.0")
}
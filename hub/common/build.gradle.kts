plugins {
    id("build-jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    api(libs.kotlinx.datetime)
    api("com.otus.otuskotlin.hub.logging:common")
}
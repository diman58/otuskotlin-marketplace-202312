plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

group = "com.otus.otuskotlin.hub.tests"
version = "0.0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

ext {
    val specDir = layout.projectDirectory.dir("../specs")
    set("spec-v1", specDir.file("specs-Offer-v1.yaml").toString())
}

tasks {
    arrayOf("build", "clean", "check").forEach {tsk ->
        create(tsk) {
            group = "build"
            dependsOn(subprojects.map {  it.getTasksByName(tsk,false)})
        }
    }
}
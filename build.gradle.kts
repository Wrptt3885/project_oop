plugins {
    base
    kotlin("multiplatform") version "2.1.20" apply false
    kotlin("plugin.serialization") version "2.1.20" apply false
    kotlin("plugin.compose") version "2.1.20" apply false
    id("org.jetbrains.compose") version "1.8.0" apply false
}

group = "com.nexfin"
version = "0.0.1-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version
}

tasks.register("buildAll") {
    group = "build"
    description = "Build all NexFin modules."
    dependsOn(":backend:build", ":frontend:build", ":shared:build")
}

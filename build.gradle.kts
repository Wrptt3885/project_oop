plugins {
    base
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

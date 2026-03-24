plugins {
    kotlin("multiplatform") version "2.1.20"
}

kotlin {
    jvm()
    sourceSets {
        val commonMain by getting
    }
}

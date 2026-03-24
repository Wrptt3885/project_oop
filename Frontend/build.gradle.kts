plugins {
    kotlin("multiplatform") version "2.1.20"
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val androidMain by creating {
            dependsOn(commonMain)
        }
        val iosMain by creating {
            dependsOn(commonMain)
        }
    }
}

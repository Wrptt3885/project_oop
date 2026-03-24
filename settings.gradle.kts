rootProject.name = "nexfin-wallet"

include(":backend")
include(":frontend")
include(":shared")

project(":backend").projectDir = file("Backend")

rootProject.name = "Trashcan"
include(
    "dumpster-common",
    "dumpster-hikari", "dumpster-sqlite",
    "dumpster-mongo"
)
include("common", "paper")
include("common-kotlin", "paper-kotlin")
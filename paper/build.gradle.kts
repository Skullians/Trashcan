import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecrell.pluginyml.GeneratePluginDescription

plugins {
    alias(libs.plugins.pluginyml.paper)
}

val compileOnlyApiLibrary = configurations.maybeCreate("compileOnlyApiLibrary")

plugins.withType<JavaPlugin> {
    extensions.getByType<SourceSetContainer>().named(SourceSet.MAIN_SOURCE_SET_NAME) {
        configurations.compileOnlyApi.get().extendsFrom(compileOnlyApiLibrary)
        configurations.library.get().extendsFrom(compileOnlyApiLibrary)
    }
}

repositories {
    maven(url = "https://repo.papermc.io/repository/maven-public/")
    maven(url = "https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    api(project(":common"))

    compileOnly(libs.bundles.paper.provided)

    compileOnlyApiLibrary(libs.bundles.paper.loaded)
    compileOnlyApiLibrary(libs.bundles.common.loaded)

    api(libs.bundles.paper.included)
    implementation(libs.bundles.paper.relocated)
}

paper {
    main = "Trashcan ftw!"
    apiVersion = "1.19"
    generateLibrariesJson = true
}

tasks {
    withType<GeneratePluginDescription> {
        librariesJsonFileName = "trashcan-libraries.json"
    }

    withType<ShadowJar> {
        dependsOn(":common:shadowJar")
        relocate("dev.triumphteam.cmd", "${rootProject.group}.commands")
        exclude("paper-plugin.yml")
    }
}

fun DependencyHandler.compileOnlyApiLibrary(dependencyNotation: Any): Dependency? =
    add("compileOnlyApiLibrary", dependencyNotation)
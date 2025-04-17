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

dependencies {
    compileOnly(project(":dumpster-common"))

    compileOnlyApiLibrary(libs.dumpster.mongo)
}

paper {
    main = "Trashcan ftw!"
    apiVersion = "1.19"
    generateLibrariesJson = true
}

tasks {
    withType<GeneratePluginDescription> {
        librariesJsonFileName = "dumpster-mongo-libraries.json"
    }

    withType<ShadowJar> {
        dependsOn(":dumpster-common:shadowJar")
        exclude("paper-plugin.yml")
    }
}

fun DependencyHandler.compileOnlyApiLibrary(dependencyNotation: Any): Dependency? =
    add("compileOnlyApiLibrary", dependencyNotation)
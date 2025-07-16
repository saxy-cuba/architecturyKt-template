plugins {
    alias(libs.plugins.shadowJar)
    alias(libs.plugins.kotlin.plugin.serialization)
    kotlin("jvm")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

configurations {
    create("common") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }

    named("compileClasspath").configure {
        extendsFrom(configurations["common"])
    }
    named("runtimeClasspath").configure {
        extendsFrom(configurations["common"])
    }
    named("developmentFabric").configure {
        extendsFrom(configurations["common"])
    }
    create("shadowBundle") {
        isCanBeResolved = true
        isCanBeConsumed = false
    }
}

repositories {
    maven("https://jitpack.io")
}

dependencies {
    modImplementation(libs.fabric.loader)
    modImplementation(libs.fabric.api)
    modImplementation(libs.architectury.fabric)
    modImplementation(libs.adventure.platform.fabric)
    implementation(kotlin("stdlib"))

    implementation(libs.adventure.minimessage)
    implementation(libs.adventure.legacy)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.okhttp)
    implementation(libs.kotlinx.datetime)

    "common"(project(path = ":commonMod", configuration = "namedElements")) { isTransitive = false }
    "shadowBundle"(project(path = ":commonMod", configuration = "transformProductionFabric"))
}

tasks.processResources {
    inputs.property("version", project.version)
    filesMatching("fabric.mod.json") {
        expand(mapOf("version" to project.version))
    }
}

tasks.shadowJar {
    configurations = listOf(project.configurations.getByName("shadowBundle"))
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    inputFile.set(tasks.shadowJar.flatMap { it.archiveFile })
}
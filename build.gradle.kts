import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import net.minecraftforge.gradle.user.ReobfMappingType

plugins {
    infix fun PluginDependencySpec.from(artifact: String?): PluginDependencySpec = version("useModule@$artifact")

    java
    kotlin("jvm") version "1.8.0"
    id("com.github.johnrengelman.shadow") version "6.1.0"
    id("net.minecraftforge.gradle.forge") from "com.github.debuggingss:ForgeGradle:FG_2.1-SNAPSHOT"
    id("org.spongepowered.mixin") from "com.github.xcfrg:MixinGradle:0.6-SNAPSHOT"
}

sourceSets {
    main {
        extra["refMap"] = "examplemod.mixins.refmap.json"
        output.setResourcesDir(java.outputDir)
    }
}

minecraft {
    version = "1.8.9-11.15.1.2318-1.8.9"
    mappings = "stable_22"
    makeObfSourceJar = false

    clientJvmArgs.add("-Delementa.dev=true")
    clientRunArgs.add("--tweakClass gg.essential.loader.stage0.EssentialSetupTweaker")
    clientRunArgs.add("--mixin examplemod.mixins.json")
}

val embed: Configuration by configurations.creating {
    configurations.implementation.get().extendsFrom(this)
}

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://repo.sk1er.club/repository/maven-public/")
    maven("https://repo.spongepowered.org/repository/maven-public/")
}

dependencies {
    embed("gg.essential:loader-launchwrapper:1.1.3")
    compileOnly("gg.essential:essential-1.8.9-forge:1759")

    annotationProcessor("org.spongepowered:mixin:0.8.5:processor")
    compileOnly("org.spongepowered:mixin:0.8.5")
}

reobf {
    create("shadowJar")
    all {
        mappingType = ReobfMappingType.SEARGE
    }
}

tasks {
    reobfJar {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveFileName.set(jar.get().archiveFileName)
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        manifest.attributes(
            "ForceLoadAsMod" to true,
            "ModSide" to "CLIENT",
            "TweakClass" to "gg.essential.loader.stage0.EssentialSetupTweaker",
            "MixinConfigs" to "examplemod.mixins.json"
        )

        configurations = listOf(embed)
    }

    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            // todo - nothing to edit here for now, but the possibility remains
        }
    }

    withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
        options.encoding = "UTF-8"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}
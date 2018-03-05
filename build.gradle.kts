import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "Top-level project for scs-problems-demo"

plugins {
    kotlin("jvm") version "1.2.30" apply false
}

allprojects {
    group = "demo"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = kotlin.collections.listOf("-Xjsr305=strict")
            }
        }
    }
}

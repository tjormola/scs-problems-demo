import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "Integration tests for scs-problems-demo"

buildscript {
    repositories {
        mavenCentral()
        maven("https://repo.spring.io/milestone")
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.0.0.RC2")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.1.0")
    }
}

apply {
    plugin("org.springframework.boot")
    plugin("org.junit.platform.gradle.plugin")
}

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.spring") version "1.2.30"
    id("io.spring.dependency-management") version "1.0.4.RELEASE"
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:Finchley.M8")
    }
}

dependencies {
    compile(project(":test-support"))
    compile(project(":http-source"))
    compile(project(":reactive-processor"))
    compile("org.springframework.boot:spring-boot-starter")
    compile("org.springframework.cloud:spring-cloud-stream-test-support")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    runtime("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    runtime("org.springframework.cloud:spring-cloud-stream-binder-kafka")
    // TODO: Remove s-k dependency after transitive dependencies picking 2.1.4
    runtime("org.springframework.kafka:spring-kafka:2.1.4.RELEASE")
    // TODO: Remove s-i dependencies after transitive dependencies are picking s-i 5.0.3
    runtime("org.springframework.integration:spring-integration-webflux:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-http:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-jmx:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-core:5.0.3.RELEASE")
    // TODO: Remove versioning from s-k dependency after transitive dependencies picking 2.1.4
    testCompile("org.springframework.kafka:spring-kafka-test:2.1.4.RELEASE")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.junit.jupiter:junit-jupiter-api")
    testRuntime("org.springframework.boot:spring-boot-starter-actuator")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<BootJar> {
    enabled = false
}
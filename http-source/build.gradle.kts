description = "WebFlux listener for scs-problems-demo"

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
    compile("org.springframework.boot:spring-boot-starter-webflux")
    compile("org.springframework.cloud:spring-cloud-stream-reactive")
    // TODO: Remove version number and dependencies other than webflux of s-i after transitive dependencies picking 5.0.3
    compile("org.springframework.integration:spring-integration-webflux:5.0.3.RELEASE")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
    runtime("org.springframework.cloud:spring-cloud-stream-binder-rabbit")
    runtime("org.springframework.cloud:spring-cloud-stream-binder-kafka")
    // TODO: Remove s-k dependency after transitive dependencies picking 2.1.4
    runtime("org.springframework.kafka:spring-kafka:2.1.4.RELEASE")
    runtime("org.springframework.integration:spring-integration-http:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-jmx:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-core:5.0.3.RELEASE")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.junit.jupiter:junit-jupiter-api")
    testRuntime("org.springframework.cloud:spring-cloud-stream-test-support")
    testRuntime("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<Jar> {
    enabled = true
}

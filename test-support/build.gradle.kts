description = "Common testing support code for scs-problems-demo"

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
    compile("org.springframework.cloud:spring-cloud-stream")
    // TODO: Remove version number and dependencies other than core of s-i after transitive dependencies picking 5.0.3
    compile("org.springframework.integration:spring-integration-core:5.0.3.RELEASE")
    runtime("org.springframework.integration:spring-integration-jmx:5.0.3.RELEASE")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    compile("org.jetbrains.kotlin:kotlin-reflect")
}
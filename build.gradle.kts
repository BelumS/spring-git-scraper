import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:2.7.7")
    }
}

plugins {
    java
    idea
    id("org.springframework.boot") version "2.7.7"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.github.spotbugs") version "5.0.13"
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
    kotlin("plugin.allopen") version "1.8.0"
    kotlin("kapt") version "1.8.0"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

group = "com.bemsa"
version = "1.0.0"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.retry:spring-retry:1.3.2")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit:junit")
        exclude("org.junit.jupiter:junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testRuntimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    failFast = true
    reports {
        junitXml.required.set(false)
        html.required.set(true)
    }
    testLogging {
        showCauses = true
        showExceptions = true
        showStackTraces = true
        showStandardStreams = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("passed", "skipped", "failed")
    }
}

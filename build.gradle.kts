import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//TODO: fix buildscript
//buildscript {
//    ext {
//        rootPack = 'com.bemsa.scraper'
//        springBootVersion = '2.7.7'
//        swaggerVersion = '3.0.0'
//    }
//
//    repositories {
//        mavenCentral()
//    }
//
//    dependencies {
//        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
//    }
//}

plugins {
    id("java")
    id("idea")
    id("org.springframework.boot") version "2.7.7"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("com.github.spotbugs") version "5.0.13"
    kotlin("jvm") version "1.8.0"
    kotlin("plugin.spring") version "1.8.0"
    kotlin("plugin.jpa") version "1.8.0"
    kotlin("plugin.allopen") version "1.8.0"
    kotlin("kapt") version "1.8.0"
}

//TODO: fix this
//idea {
//    module {
//        downloadJavadoc = true
//        downloadSources = true
//    }
//}

group = "com.bemsa"
version = "1.0.0"
java.sourceCompatibility = JavaVersion.VERSION_11

//TODO: fix this
//configurations {
//    developmentOnly
//    runtimeClasspath {
//        extendsFrom developmentOnly
//    }
//    compileOnly {
//        extendsFrom annotationProcessor
//    }
//}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
//    implementation("ch.qos.logback:logback-classic:1.4.5")

    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-webflux")
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
    testImplementation("com.ninja-squad:springmockk:4.0.1")
    testImplementation("org.springframework.restdocs:spring-restdocs-webtestclient")
    testRuntimeOnly("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.4")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    //TODO: fix this
    testLogging {
//        events = listOf("passed", "skipped", "failed")
    }
}

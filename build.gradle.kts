import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
}

group = "com.beside"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.0.5")
    implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web:3.0.5")
    implementation("org.springframework.boot:spring-boot-starter-validation:3.0.5")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
//    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.4")
    implementation("com.amazonaws:aws-java-sdk-s3:1.12.435")
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")

    // https://mvnrepository.com/artifact/io.kotest/kotest-assertions-core-jvm
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.5.5")
    // https://mvnrepository.com/artifact/io.kotest/kotest-runner-junit5-jvm
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.5.5")

    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.13.4")
    testImplementation("io.mockk:mockk-jvm:1.13.4")
    testImplementation("io.mockk:mockk-agent-jvm:1.13.4")


    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

import zip.ntoj.buildsupport.bundle
import zip.ntoj.buildsupport.lib

plugins {
    id("configure-kotlin")
    id("configure-ktlint")
    id("configure-groovy")
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
    id("org.flywaydb.flyway") version "9.22.3"
}

dependencies {
    implementation(project(":shared"))
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("com.github.ben-manes.caffeine:caffeine")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.flywaydb:flyway-database-postgresql:10.0.0")
    implementation(lib("saToken-spring"))
    implementation(lib("saToken-jwt"))
    implementation(lib("commons-io"))
    testImplementation(bundle("spock"))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

flyway {
    url = "jdbc:postgresql://${System.getenv("PG_HOST") ?: "localhost"}:${System.getenv("PG_PORT") ?: "15432"}/${
        System.getenv("PG_DATABASE") ?: "ntoj"
    }"
    user = System.getenv("PG_USER") ?: "ntoj"
    password = System.getenv("PG_PASSWORD") ?: "123456"
    schemas = arrayOf("public")
}

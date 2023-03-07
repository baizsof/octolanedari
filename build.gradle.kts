import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    application
}

group = "com.balazsizsof"
version = "1.0.0"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.5")
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation("org.json:json:20220924")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("joda-time:joda-time:2.12.2")

    implementation("com.google.api-client:google-api-client:2.0.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-calendar:v3-rev20220715-2.0.0")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClassName = "calendari.Octolendari"
}
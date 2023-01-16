import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "com.balazsizsof"
version = "0.1.0"


repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.httpcomponents:httpclient:4.5.13")
    implementation ("com.google.code.gson:gson:2.10")
    implementation("org.json:json:20220924")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("joda-time:joda-time:2.12.2")

    testImplementation("pl.pragmatists:JUnitParams:1.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}
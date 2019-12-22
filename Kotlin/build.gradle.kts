import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.3.61"
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter","junit-jupiter-api","5.4.2")
    testRuntime("org.junit.jupiter","junit-jupiter-engine","5.4.2")
}

tasks.test {
    useJUnitPlatform()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
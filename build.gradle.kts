import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.20"
    id("antlr")
}

group = "homework"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()

    mavenCentral()
}


tasks.compileKotlin {
    dependsOn("generateGrammarSource")
}

tasks.generateGrammarSource {
//    maxHeapSize = "64m"
    arguments = arguments + listOf("-visitor", "-long-messages")
}



dependencies {
    antlr("org.antlr:antlr4:4.12.0")
    implementation("org.antlr:antlr4:4.12.0")
    implementation("org.jgrapht:jgrapht-core:1.5.1")

    implementation("org.jetbrains.kotlinx:dataframe:0.9.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    languageVersion = "1.9"
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    languageVersion = "1.9"
}
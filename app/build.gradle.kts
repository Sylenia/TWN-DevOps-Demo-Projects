plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // OSHI Core library
    implementation("com.github.oshi:oshi-core:6.4.4")

    // Java Native Access (JNA) libraries
    implementation("net.java.dev.jna:jna:5.13.0")
    implementation("net.java.dev.jna:jna-platform:5.13.0")

    // SLF4J API for logging
    implementation("org.slf4j:slf4j-api:1.7.32")

    // SLF4J Simple implementation for logging
    runtimeOnly("org.slf4j:slf4j-simple:1.7.32")

    // SparkJava for lightweight web server
    implementation("com.sparkjava:spark-core:2.9.4")

    // JUnit Jupiter for testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    // Define the main class for the application.
    mainClass.set("org.example.App") 
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks {
    shadowJar {
        archiveBaseName.set("app")
        archiveVersion.set("")
        archiveClassifier.set("")
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.example.App"
    }
}

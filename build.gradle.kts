plugins {
    java
}

group = "io.github.talkarcabbage.logger"
version = project.property("version") as String

repositories {
    mavenCentral()
}

dependencies {
    // Add your dependencies here
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java.srcDirs("src/main/java")
    }
    test {
        java.srcDirs("src/test/java")
    }
}

java {
    withSourcesJar()
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version
        )
    }
}
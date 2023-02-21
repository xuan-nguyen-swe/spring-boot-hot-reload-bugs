import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  java
  id("org.springframework.boot") version "3.0.2"
  id("io.spring.dependency-management") version "1.1.0"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  implementation(project(":lib"))
}

tasks {
  val bootRun by getting(BootRun::class) {
    doFirst {
      logger.debug(project(":lib").buildDir.absolutePath)
    }

    systemProperty(
      "spring.devtools.restart.additional-paths",
      project(":lib").buildDir.absolutePath
    )
  }
}

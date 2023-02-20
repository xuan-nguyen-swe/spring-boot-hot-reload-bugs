import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  id("org.springframework.boot") version "3.0.2"
  id("io.spring.dependency-management") version "1.1.0"
  kotlin("jvm") version "1.7.22"
  kotlin("plugin.spring") version "1.7.22"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
  mavenCentral()
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

  developmentOnly("org.springframework.boot:spring-boot-devtools")

  implementation(project(":lib"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "17"
  }
}

tasks {
  val bootRun by getting(BootRun::class) {
    doFirst {
      println(project(":lib").buildDir.absolutePath)
    }

    systemProperty(
      "spring.devtools.restart.additional-paths",
      project(":lib").buildDir.absolutePath
    )
  }
}

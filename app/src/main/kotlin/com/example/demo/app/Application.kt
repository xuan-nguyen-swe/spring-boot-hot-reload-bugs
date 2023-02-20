package com.example.demo.app

import com.example.demo.lib.LibHealth
import com.example.demo.lib.LibHealthService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@SpringBootApplication(
  scanBasePackageClasses = [Application::class, LibHealthService::class]
)
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}

@Controller
class HealthController(
  private val libHealthService: LibHealthService,
) {
  @GetMapping("/health")
  fun health() = ResponseEntity.ok(
    mapOf(
      "app" to "OK",
      "lib" to LibHealth.health(),
      "libService" to libHealthService.health(),
    )
  )
}
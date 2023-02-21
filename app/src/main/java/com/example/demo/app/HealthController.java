package com.example.demo.app;

import com.example.demo.lib.LibHealth;
import com.example.demo.lib.LibHealthService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HealthController {
  private final LibHealthService libHealthService;

  public HealthController(LibHealthService libHealthService) {
    this.libHealthService = libHealthService;
  }

  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(new HashMap<>() {{
      put("app", "OK");
      put("lib", LibHealth.health());
      put("libService", libHealthService.health());
    }});
  }
}

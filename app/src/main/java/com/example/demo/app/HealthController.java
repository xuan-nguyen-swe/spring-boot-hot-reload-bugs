package com.example.demo.app;

import com.example.demo.lib.LibHealth;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HealthController {
  @GetMapping("/health")
  public ResponseEntity<Map<String, String>> health() {
    return ResponseEntity.ok(new HashMap<>() {{
      put("app", "OK 1");
      put("lib", LibHealth.health());
    }});
  }
}

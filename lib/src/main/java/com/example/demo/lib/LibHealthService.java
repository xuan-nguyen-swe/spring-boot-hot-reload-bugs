package com.example.demo.lib;

import org.springframework.stereotype.Service;

@Service
public class LibHealthService {
  public String health() {
    return "OK";
  }
}

package com.example.demo.lib

import org.springframework.stereotype.Service

@Service
class LibHealthService {
  fun health() = "OK"
}
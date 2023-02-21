# How to reproduce bug

* Start Spring Boot application with command `./gradlew :app:bootRun`
* `GET http://localhost:8080/health` should response

```
{
  "app": "OK",
  "lib": "OK"
}
```

* Change `HealthController` with the new code below

```
@GetMapping("/health")
public ResponseEntity<Map<String, String>> health() {
  return ResponseEntity.ok(new HashMap<>() {{
    put("app", "OK 1");
    put("lib", LibHealth.health());
  }});
}
```

* Change `LibHealth` with the new code below

```
public class LibHealth {
  public static String health() {
    return "OK 1";
  }
}
```

* Run command `./gradlew :app:compileJava`, Spring Boot application reloads with an error message

* `GET http://localhost:8080/health` returns incorrect response

```
{
  "app": "OK 1",
  "lib": "OK"
}
```
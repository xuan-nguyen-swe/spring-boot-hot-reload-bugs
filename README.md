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
@Controller
class HealthController {
  @GetMapping("/health")
  fun health() = ResponseEntity.ok(
    mapOf(
      "app" to "OK 1",
      "lib" to LibHealth.health(),
    )
  )
}
```

* Change `LibHealth` with the new code below

```
object LibHealth {
  fun health() = "OK 1"
}
```

* Run command `./gradlew :app:compileKotlin`, Spring Boot application should reload successfully

* `GET http://localhost:8080/health` should response

```
{
  "app": "OK 1",
  "lib": "OK 1"
}
```

* Change `LibHealth` with the new code below

```
object LibHealth {
  fun health() = "OK 2"
}
```

* Run command `./gradlew :app:compileKotlin`, Spring Boot application should reload.
  This time, it has error while reloading, but still reload successfully at the end.

```
2023-02-20T17:55:18.990+01:00 ERROR 46860 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed

java.lang.NullPointerException: Cannot read field "generatedClass" because "data" is null
        at org.springframework.cglib.proxy.Enhancer.nextInstance(Enhancer.java:783) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:317) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:562) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:407) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassEnhancer.createClass(ConfigurationClassEnhancer.java:138) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassEnhancer.enhance(ConfigurationClassEnhancer.java:109) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.enhanceConfigurationClasses(ConfigurationClassPostProcessor.java:514) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanFactory(ConfigurationClassPostProcessor.java:304) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:358) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:150) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:745) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:565) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:432) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:308) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291) ~[spring-boot-3.0.2.jar:3.0.2]
        at com.example.demo.app.ApplicationKt.main(Application.kt:27) ~[main/:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.boot.devtools.restart.RestartLauncher.run(RestartLauncher.java:49) ~[spring-boot-devtools-3.0.2.jar:3.0.2]
```

* `GET http://localhost:8080/health` still responses correctly as below

```
{
  "app": "OK 1",
  "lib": "OK 2"
}
```
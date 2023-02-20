# How to reproduce bug

* Start Spring Boot application with command `./gradlew :app:bootRun`
* `GET http://localhost:8080/health` should response

```
{
  "app": "OK",
  "lib": "OK",
  "libService": "OK"
}
```

* Change `HealthController` with the new code below

```
@Controller
class HealthController(
  private val libHealthService: LibHealthService,
) {
  @GetMapping("/health")
  fun health() = ResponseEntity.ok(
    mapOf(
      "app" to "OK 1",
      "lib" to LibHealth.health(),
      "libService" to libHealthService.health(),
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

* Change `LibHealthService` with the new code below

```
@Service
class LibHealthService {
  fun health() = "OK 1"
}
```

* Run command `./gradlew :app:compileKotlin`
* Spring should trigger reloading, but fail with error below

```
2023-02-20T18:39:00.071+01:00 ERROR 24256 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanDefinitionStoreException: Failed to parse configuration class [com.example.demo.app.Application]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:178) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:398) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:283) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:344) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:115) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:745) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:565) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:432) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:308) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291) ~[spring-boot-3.0.2.jar:3.0.2]
        at com.example.demo.app.ApplicationKt.main(Application.kt:33) ~[main/:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.createMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:86) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.getMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:73) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:81) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:187) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:297) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:243) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:196) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:164) ~[spring-context-6.0.4.jar:6.0.4]
        ... 18 common frames omitted
```
* `GET http://localhost:8080/health` doesn't work anymore
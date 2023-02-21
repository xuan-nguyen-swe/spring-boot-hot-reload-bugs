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
@GetMapping("/health")
public ResponseEntity<Map<String, String>> health() {
  return ResponseEntity.ok(new HashMap<>() {{
    put("app", "OK 1");
    put("lib", LibHealth.health());
    put("libService", libHealthService.health());
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

* Change `LibHealthService` with the new code below

```
@Service
public class LibHealthService {
  public String health() {
    return "OK 1";
  }
}
```

* Run command `./gradlew :app:compileJava`, Spring Boot application reloads with an error message

```
2023-02-21T14:45:20.907+01:00 ERROR 6296 --- [  restartedMain] o.s.boot.SpringApplication               : Application run failed

org.springframework.context.ApplicationContextException: Unable to start web server
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh(ServletWebServerApplicationContext.java:164) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:578) ~[spring-context-6.0.4.jar:6.0.4]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:730) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:432) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:308) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1302) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1291) ~[spring-boot-3.0.2.jar:3.0.2]
        at com.example.demo.app.Application.main(Application.java:12) ~[main/:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
        at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77) ~[na:na]
        at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
        at java.base/java.lang.reflect.Method.invoke(Method.java:568) ~[na:na]
        at org.springframework.boot.devtools.restart.RestartLauncher.run(RestartLauncher.java:49) ~[spring-boot-devtools-3.0.2.jar:3.0.2]
Caused by: org.springframework.beans.factory.CannotLoadBeanClassException: Cannot find class [com.example.demo.app.HealthController] for bean with name 'healthController' defined in file [C:\Workspace\Projects\private\Self-trainings
\spring-boot-hot-reload-bugs\app\build\classes\java\main\com\example\demo\app\HealthController.class]
        at org.springframework.beans.factory.support.AbstractBeanFactory.resolveBeanClass(AbstractBeanFactory.java:1505) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.determineTargetType(AbstractAutowireCapableBeanFactory.java:684) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.predictBeanType(AbstractAutowireCapableBeanFactory.java:652) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.isFactoryBean(AbstractBeanFactory.java:1632) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.doGetBeanNamesForType(DefaultListableBeanFactory.java:559) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanNamesForType(DefaultListableBeanFactory.java:531) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.getBeanNamesForType(DefaultListableBeanFactory.java:525) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.getWebServerFactory(ServletWebServerApplicationContext.java:209) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.createWebServer(ServletWebServerApplicationContext.java:181) ~[spring-boot-3.0.2.jar:3.0.2]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh(ServletWebServerApplicationContext.java:161) ~[spring-boot-3.0.2.jar:3.0.2]
        ... 13 common frames omitted
Caused by: java.lang.ClassNotFoundException: com.example.demo.app.HealthController
        at java.base/jdk.internal.loader.BuiltinClassLoader.loadClass(BuiltinClassLoader.java:641) ~[na:na]
        at java.base/jdk.internal.loader.ClassLoaders$AppClassLoader.loadClass(ClassLoaders.java:188) ~[na:na]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520) ~[na:na]
        at java.base/java.lang.Class.forName0(Native Method) ~[na:na]
        at java.base/java.lang.Class.forName(Class.java:467) ~[na:na]
        at org.springframework.boot.devtools.restart.classloader.RestartClassLoader.loadClass(RestartClassLoader.java:121) ~[spring-boot-devtools-3.0.2.jar:3.0.2]
        at java.base/java.lang.ClassLoader.loadClass(ClassLoader.java:520) ~[na:na]
        at java.base/java.lang.Class.forName0(Native Method) ~[na:na]
        at java.base/java.lang.Class.forName(Class.java:467) ~[na:na]
        at org.springframework.util.ClassUtils.forName(ClassUtils.java:283) ~[spring-core-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanDefinition.resolveBeanClass(AbstractBeanDefinition.java:461) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doResolveBeanClass(AbstractBeanFactory.java:1569) ~[spring-beans-6.0.4.jar:6.0.4]
        at org.springframework.beans.factory.support.AbstractBeanFactory.resolveBeanClass(AbstractBeanFactory.java:1502) ~[spring-beans-6.0.4.jar:6.0.4]
        ... 22 common frames omitted
```

* `GET http://localhost:8080/health` returns incorrect response

```
{
  "app": "OK 1",
  "lib": "OK",
  "libService": "OK"
}
```
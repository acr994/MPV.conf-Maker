# MPV.conf-Maker

Aplicación Android en Kotlin para generar visualmente archivos `mpv.conf` adecuados para Android.

## Estado

Estructura base inicial del proyecto:

- Kotlin
- Jetpack Compose
- Material 3
- Gradle Kotlin DSL
- Package: `com.acr.mpvconfmaker`

La pantalla principal incluye el título de la app, una descripción breve y botones para futuras acciones de creación y preview.

## Compilar

```bash
gradle assembleDebug
```

Este repositorio no versiona `gradle/wrapper/gradle-wrapper.jar` ni otros binarios. En el entorno de Codex se debe usar el Gradle instalado por el sistema. Si quieres usar `./gradlew` en otro entorno, regenera el wrapper localmente y no incluyas el JAR en el PR.

> No se debe versionar `local.properties`; cada entorno debe definir su propio `sdk.dir` o `ANDROID_HOME`.

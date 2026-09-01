# Decisiones técnicas

Registro de las decisiones de diseño del proyecto y de las versiones fijadas.
Si algo cambia, actualizar este archivo y la tabla del README.

## Versiones fijadas

| Componente | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java (JDK) | 17 (LTS) — build con `release 17` |
| Build | Maven (Maven Wrapper) | 3.9.14 — wrapper 3.3.4 (`only-script`) |
| Lexer | JFlex (`jflex-maven-plugin`) | 1.9.1 |
| Parser | JAVA CUP (`cup-maven-plugin`) | 11b-20160615-3 |
| Runtime CUP | `java-cup-runtime` | 11b-20160615-3 |
| GUI | Swing | incluida en JDK 17 |
| Testing | JUnit 5 (Jupiter) | 5.11.3 |
| Tests runner | `maven-surefire-plugin` | 3.5.2 |
| Empaquetado | `maven-shade-plugin` | 3.5.0 |
| Compilación | `maven-compiler-plugin` | 3.13.0 |
| Fuentes generadas | `build-helper-maven-plugin` | 3.6.0 |
| CI | GitHub Actions | `checkout@v4`, `setup-java@v4` (Temurin 17) |
| Hosting | GitHub | repo `teoria1-grupo5` |

## Decisiones de diseño

1. **Build: Maven** — estándar de facto para Java, plugin oficial de JFlex,
   importación universal en IDEs.
2. **GUI: Swing** — viene con el JDK (cero dependencias extra); suficiente para
   el IDE pedido (editor + botones + panel de salida). JavaFX se descartó por
   agregar runtime y complejidad de empaquetado sin beneficio para el alcance.
3. **Hosting: GitHub** — Actions da CI sin servicios externos y los Pull
   Requests encajan con el flujo de ramas elegido.
4. **JDK 17** — LTS con soporte vigente; `release 17` en el compilador hace que
   el resultado sea el mismo compile quien compile (incluso con JDK 21/25 local).
5. **Código JFlex generado en build** — la fuente de verdad es `Lexico.flex`;
   `Lexico.java` vive en `target/` (gitignored). Evita diffs ruidosos y
   conflictos de merge sobre código generado.
6. **Empaquetado: maven-shade-plugin** — un solo JAR ejecutable con
   `Main-Class: unlu.teoi.grupo5.main.Main`; entregable directo de la consigna.
7. **Paquetes por capa** (`lexer`, `gui`, `symboltable`, `main`) — simple y
   suficiente para el tamaño del TP; organizar por feature sería sobre-ingeniería.
8. **Ubicación de entregables mixta** — `Lexico.flex` junto al código (lo
   procesa el build); `prueba.txt` y `ts.txt` en `docs/entrega1/` (documentos).
9. **Testing: JUnit 5** — estándar actual; surefire 3.x lo detecta solo.
10. **Ramas: main + develop** — `main` protegida y solo con merges en entregas;
    `develop` de integración continua; features por tema/integrante.
11. **Analizador sintáctico: JAVA CUP** — obligatorio por consigna para la
    entrega 2. Se usa `cup-maven-plugin` (mismo autor que el runtime), que
    genera `Parser.java` y `sym.java` en `target/generated-sources/cup` en la
    fase `generate-sources`, igual que JFlex; `build-helper-maven-plugin` agrega
    esa carpeta como raíz de fuentes. El archivo fuente se llama
    `Sintactico.cup` (nombre exigido por la consigna) y vive en `src/main/cup/`.
12. **Integración lexer↔parser con `%cup`** — `Lexico.flex` compila en modo
    CUP: devuelve `java_cup.runtime.Symbol` y usa la interfaz `sym` generada
    por CUP (`%cupsym unlu.teoi.grupo5.parser.sym`). Se hace desde el kickstart
    para no reescribir las reglas léxicas en la entrega 2, cuando ya estén
    completas.

## Decisiones operativas

- **Maven Wrapper incluido** — todo el equipo usa la misma versión de Maven sin
  instalarla; imprescindible con "varios IDEs".
- **CI automático** — `./mvnw -B verify` en push/PR a `main` y `develop`
  (genera lexer + compila + tests), con Temurin 17.
- **Coordenadas Maven** — `groupId: unlu.teoi.grupo5`, `artifactId: teoi-grupo5`.

## Diferencias puntuales con el resumen de la conversación previa

- **`maven.compiler.release=17` en lugar de `source`/`target`**: es el
  reemplazo moderno; fija también el API level, de modo que compilar con un JDK
  más nuevo no puede colar accidentalmente APIs de JDK 21+ que romperían el CI
  (que corre con JDK 17 exacto).
- **Maven Wrapper `only-script` (3.3.4) sin `maven-wrapper.jar`**: modo oficial
  del wrapper moderno; el script descarga Maven directamente y no se versiona
  ningún binario en el repo. Misma experiencia de uso (`./mvnw`).
- **Nombre del repo GitHub**: el repo ya existe como `teoria1-grupo5` (remote
  configurado) y coincide con la carpeta local; se mantiene ese nombre. Las
  coordenadas Maven (`unlu.teoi.grupo5:teoi-grupo5`) quedan como se acordó.
- **Versiones concretas**: se pinnean versiones estables compatibles con JDK 17
  (tabla de arriba); cambiarlas es editar una línea en el `pom.xml`.

## Pendientes manuales (GitHub, una sola vez)

- Protección de ramas y default branch: ver `docs/ejecucion.md`.
- Primer push de `main` y `develop` al remote.

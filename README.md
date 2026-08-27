# teoria1-grupo5

Trabajo Práctico **Compilador** — Teoría de la Computación I (UNLu, 2026), **Grupo 5**.
Tema especial asignado: **SUMAIMPAR**.

> Estado actual: **kickstart de la entrega 1** (8/10/26). El proyecto compila,
> genera el analizador léxico, corre tests y empaqueta un JAR ejecutable, pero
> **no contiene la lógica del TP**: eso se escribe a mano de acá en adelante.

- **Integrantes**: Abril Nadia Babino, Naiara Agustina Collazo, Nicolás Guillermo Huici, María Agustina Ortiz, Dante Juan Terranova.
- **Consigna**: [`Consigna.md`](Consigna.md)

## Tecnologías y versiones

| Componente | Tecnología | Versión |
|---|---|---|
| Lenguaje | Java (JDK) | **17** (LTS; el build fija `release 17`) |
| Build | Maven (vía Maven Wrapper, no requiere instalación) | 3.9.14 (wrapper 3.3.4, modo `only-script`) |
| Generador de lexer | JFlex (`jflex-maven-plugin`) | 1.9.1 |
| GUI | Swing (`java.desktop`, incluida en el JDK) | incluida en JDK 17 |
| Testing | JUnit 5 (Jupiter) | 5.11.3 |
| Runner de tests | `maven-surefire-plugin` | 3.5.2 |
| Empaquetado | `maven-shade-plugin` (JAR ejecutable) | 3.5.0 |
| Compilación | `maven-compiler-plugin` | 3.13.0 |
| Fuentes generadas | `build-helper-maven-plugin` | 3.6.0 |
| CI | GitHub Actions (`actions/checkout@v4`, `actions/setup-java@v4` + Temurin 17) | — |
| Hosting Git | GitHub | — |

Justificación de cada elección: [`docs/decisiones-tecnicas.md`](docs/decisiones-tecnicas.md).

## Requisitos

- JDK 17 o superior (recomendado Temurin 17; el CI usa exactamente esa).
- Git.
- Maven **no** hace falta instalarlo: `./mvnw` lo descarga solo la primera vez.
- Internet únicamente en el primer build.

## Cómo correrlo

```bash
./mvnw verify    # genera el lexer (JFlex), compila y corre los tests
./mvnw package   # igual que verify + empaqueta el JAR ejecutable
java -jar target/teoi-grupo5-1.0-SNAPSHOT.jar
```

En Windows: `mvnw.cmd verify`. Guía detallada (IDEs, CI, flujo Git):
[`docs/ejecucion.md`](docs/ejecucion.md).

## Estructura del repositorio

```
teoria1-grupo5/
├── .github/workflows/build.yml      # CI: build + tests en main/develop
├── .mvn/wrapper/                    # Maven Wrapper (solo script, sin .jar)
├── docs/
│   ├── arquitectura.md              # qué hace cada componente
│   ├── decisiones-tecnicas.md       # decisiones + versiones, con justificación
│   ├── ejecucion.md                 # cómo correr: comandos, IDEs, CI, Git
│   └── entrega1/                    # entregables de la 1ra entrega
│       ├── prueba.txt
│       └── ts.txt
├── src/
│   ├── main/
│   │   ├── java/unlu/teoi/grupo5/
│   │   │   ├── gui/                 # interfaz Swing (JTextArea, botones, salida)
│   │   │   ├── lexer/               # soporte del analizador léxico
│   │   │   ├── main/Main.java       # punto de entrada
│   │   │   └── symboltable/         # tabla de símbolos
│   │   └── jflex/Lexico.flex        # especificación léxica (lo que se edita)
│   └── test/java/unlu/teoi/grupo5/lexer/LexicoTest.java
├── Consigna.md
├── .gitignore
├── mvnw / mvnw.cmd
├── pom.xml
└── README.md
```

## Arquitectura (resumen)

- **`lexer`**: la especificación `Lexico.flex` (fuente de verdad) y el soporte
  del lexer. `Lexico.java` se genera en `target/` en cada build y no se versiona.
- **`gui`**: interfaz Swing — cuadro de texto para el código, botones de
  cargar/compilar, panel de salida con tokens y errores.
- **`symboltable`**: tabla de símbolos (columnas NOMBRE/TOKEN/TIPO/VALOR/LONG
  según consigna) con inserción y consulta.
- **`main`**: `Main`, el punto de entrada que instancia la GUI.

Detalle: [`docs/arquitectura.md`](docs/arquitectura.md).

## Flujo Git

- `main`: protegida; solo recibe merges desde `develop` en fechas de entrega
  (**8/10/26** y **23/11/26**).
- `develop`: integración continua del equipo; siempre en verde.
- Ramas `feature/<tema>` → Pull Request → `develop`.

## Entregas

- **Entrega 1** (8/10/26): `Lexico.flex`, `prueba.txt` y `ts.txt`
  (`docs/entrega1/`), código fuente y JAR ejecutable (generado on-demand con
  `./mvnw package`).
- **Entrega 2** (23/11/26): agrega `Sintactico.cup` (JAVA CUP).

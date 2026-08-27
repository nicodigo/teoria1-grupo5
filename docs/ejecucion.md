# Cómo correr el proyecto

## Requisitos

- **JDK 17 o superior** (recomendado: Temurin 17, el mismo que usa el CI).
  Verificar con `java -version`.
- **Git**.
- **Maven no es necesario**: el Maven Wrapper (`./mvnw`) descarga la versión
  exacta de Maven (3.9.14) la primera vez.
- Internet solo en el primer build (descarga de Maven y dependencias a `~/.m2`).

## Primer build

```bash
./mvnw verify
```

Secuencia: descarga Maven 3.9.14 (solo la primera vez) → genera el lexer con
JFlex → compila con `release 17` → corre los tests JUnit 5.

En Windows usar `mvnw.cmd verify` (CMD o PowerShell).

## Comandos habituales

| Comando | Efecto |
|---|---|
| `./mvnw verify` | genera el lexer + compila + corre los tests |
| `./mvnw test` | compila y corre solo los tests |
| `./mvnw package` | verify + empaqueta el JAR ejecutable |
| `java -jar target/teoi-grupo5-1.0-SNAPSHOT.jar` | ejecuta la aplicación |
| `./mvnw -DskipTests package` | empaqueta sin correr tests |
| `./mvnw clean` | borra `target/` (incluido el lexer generado; se regenera solo) |

## IDEs

El proyecto es un pom Maven estándar; cualquier IDE lo importa:

- **IntelliJ IDEA**: File → Open → carpeta del repo.
- **Eclipse**: File → Import → Maven → Existing Maven Projects.
- **VS Code**: Extension Pack for Java → abrir carpeta.
- **NetBeans**: File → Open Project → carpeta del repo.

Si el IDE marca `Lexico` como inexistente: correr `./mvnw verify` una vez
(recrea `target/generated-sources/jflex`) y refrescar el proyecto.

## GitHub Actions (CI)

`build.yml` corre `./mvnw -B verify` con JDK 17 (Temurin) en push y pull_request
sobre `main` y `develop`. Estado en la pestaña **Actions** del repo.

## Flujo Git

- `main`: protegida; solo recibe merges desde `develop` en fechas de entrega
  (8/10/26 y 23/11/26).
- `develop`: integración continua; siempre debe buildear en verde.
- `feature/<tema>`: una rama por tema/integrante.

```bash
git checkout develop
git pull
git checkout -b feature/mi-tema
# ... commits ...
git push -u origin feature/mi-tema
# abrir Pull Request hacia develop en GitHub
```

## Configuración única en GitHub (requiere admin; no se puede hacer por archivos)

1. **Branch protection** (Settings → Branches → Add rule):
   - `main`: requerir pull request antes de mergear + requerir el check `build`.
   - `develop`: requerir el check `build` (opcional: requerir PR).
2. **Sugerencia**: Settings → General → Default branch → `develop`, para que los
   Pull Requests apunten a develop por defecto y el README visible sea el de la
   rama de desarrollo.
3. **Primer push**: `git push -u origin main develop` (el remote ya está
   configurado como `origin`).

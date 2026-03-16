<div align="center">
  <h1>📝 Blog Management System</h1>
  <p>
    <img src="https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=java" alt="Java"/>
    <img src="https://img.shields.io/badge/Spring%20Boot-4.0.3-brightgreen?style=for-the-badge&logo=spring-boot" alt="Spring Boot"/>
    <img src="https://img.shields.io/badge/Maven-3.8%2B-orange?style=for-the-badge&logo=apache-maven" alt="Maven"/>
    <a href="https://opensource.org/licenses/MIT" target="_blank"><img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="MIT License"/></a>
  </p>
</div>

> Sistema profesional para la gestión de blogs, desarrollado en Java y Spring Boot. Permite administrar usuarios, publicaciones y comentarios, con autenticación y autorización seguras.

---

## 📑 Tabla de Contenidos

1. [Características](#características)
2. [Requisitos previos](#requisitos-previos)
3. [Instalación y ejecución rápida](#instalación-y-ejecución-rápida)
4. [Estructura del proyecto](#estructura-del-proyecto)
5. [Documentación](#documentación)
6. [Pruebas](#pruebas)
7. [Licencia](#licencia)

---

## ✨ Características

- Gestión de usuarios, roles y autenticación JWT 🔐
- CRUD de publicaciones y comentarios 📝
- Seguridad con Spring Security y validaciones
- Integración con PostgreSQL y H2 (test)
- Documentación y colección Postman incluidas 📬
- Scripts de base de datos listos para usar

---

## 🛠️ Requisitos previos

- Java 17 o superior
- Maven 3.8+
- Docker (opcional, para base de datos)

---

## ⚡ Instalación y ejecución rápida

1. **Clona el repositorio:**

   ```sh
   git clone <URL_DEL_REPOSITORIO>
   cd blog-management-system
   ```

2. **Configura la base de datos:**

   Puedes iniciar una base de datos PostgreSQL usando Docker:

   ```sh
   docker-compose up -d
   ```
   Esto levantará la base de datos definida en `docker-compose.yaml` y ejecutará los scripts de inicialización en `db/Init.sql`.

3. **Configura variables de entorno:**

   Revisa y ajusta los parámetros de conexión en `src/main/resources/application.yaml` si es necesario.


4. **Compila y ejecuta la aplicación:**

   ```sh
   ./mvnw spring-boot:run
   ```
   
5. **Prueba la API:**

   - La API estará disponible en: `http://localhost:8080`
   - Usa la colección Postman en `docs/postman/Blog-Management-System.postman_collection.json` para probar los endpoints.

---

## 🗂️ Estructura del proyecto

| Carpeta/Archivo | Descripción |
|-----------------|-------------|
| `src/main/java/com/mauricio/blogmanagementsystem/` | Código fuente principal |
| `src/main/resources/` | Archivos de configuración |
| `db/Init.sql` | Script de inicialización de la base de datos |
| `docs/` | Documentación y recursos |
| `docker-compose.yaml` | Configuración de base de datos con Docker |

---

## 📚 Documentación

- Consulta la documentación técnica y de endpoints en `docs/`.
- Diagramas y diseño en `docs/img/`.

---

## 🧪 Pruebas

Para ejecutar las pruebas:

```sh
./mvnw test
```

---

## 📝 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más información.
# 📬 Blog Management System — API Curls

> Colección de curls listos para importar en **Postman** o ejecutar desde terminal.
>
> **Base URL:** `http://localhost:8080`

---

## 📑 Índice

| Sección | Endpoints |
|---------|-----------|
| [Health](#-health) | Estado de la aplicación |
| [Auth](#-auth) | Register, Login |
| [Posts](#-posts) | CRUD completo |
| [Comments](#-comments) | CRUD completo |
| [Seguridad](#-pruebas-de-seguridad) | Casos de roles y permisos |
| [Validaciones](#-pruebas-de-validación) | Casos de error |

---

## 💚 Health

> **Base path:** `/actuator`
>
> ⚠️ Solo el endpoint de **health** está habilitado. No requiere token.

| Método | Endpoint | Descripción | Auth | Status |
|--------|----------|-------------|------|--------|
| `GET` | `/actuator/health` | Estado de la aplicación | ❌ | `200` |

### ▶️ Health Check

```bash
curl --location 'http://localhost:8080/actuator/health'
```

> 💡 **Respuesta esperada:**
> ```json
> {
>   "status": "UP"
> }
> ```

---

## 🔐 Auth

> **Base path:** `/api/auth`
>
> ⚠️ Las rutas de autenticación son **públicas**, no requieren token.

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| `POST` | `/api/auth/register` | Registrar usuario | `201` |
| `POST` | `/api/auth/login` | Iniciar sesión | `200` |

### ▶️ Registrar un usuario

```bash
curl --location 'http://localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "email": "usuario@email.com",
    "password": "password123"
}'
```

### ▶️ Iniciar sesión

```bash
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "email": "usuario@email.com",
    "password": "password123"
}'
```

> 💡 **Respuesta exitosa de login/register:**
> ```json
> {
>   "status": 200,
>   "message": "Inicio de sesión exitoso",
>   "data": {
>     "token": "eyJhbGciOiJIUzM4NCJ9...",
>     "tokenType": "Bearer",
>     "expiresIn": 86400,
>     "email": "usuario@email.com",
>     "role": "USER"
>   }
> }
> ```
> 📋 Copia el valor de `token` y úsalo como `Bearer Token` en los siguientes requests protegidos.

---

## 📝 Posts

> **Base path:** `/api/posts`
>
> 🔒 **Todas** las rutas requieren **Bearer Token** (USER o ADMIN)

| Método | Endpoint | Descripción | Auth | Status |
|--------|----------|-------------|------|--------|
| `GET` | `/api/posts` | Listar todos | ✅ | `200` |
| `GET` | `/api/posts/{id}` | Obtener por ID | ✅ | `200` |
| `GET` | `/api/posts/author/{authorId}` | Obtener por autor | ✅ | `200` |
| `POST` | `/api/posts` | Crear post | ✅ | `201` |
| `PUT` | `/api/posts/{id}` | Actualizar (solo dueño) | ✅ | `200` |
| `DELETE` | `/api/posts/{id}` | Eliminar soft (dueño/admin) | ✅ | `200` |

### ▶️ Crear un post 🔒

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "title": "Mi primer post",
    "content": "Este es el contenido de mi primer post en el blog"
}'
```

### ▶️ Obtener todos los posts 🔒

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### ▶️ Obtener post por ID 🔒

```bash
curl --location 'http://localhost:8080/api/posts/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### ▶️ Obtener posts por autor 🔒

```bash
curl --location 'http://localhost:8080/api/posts/author/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### ▶️ Actualizar un post 🔒

```bash
curl --location --request PUT 'http://localhost:8080/api/posts/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "title": "Mi primer post actualizado",
    "content": "Este es el contenido actualizado del post"
}'
```

### ▶️ Eliminar un post (soft delete) 🔒

```bash
curl --location --request DELETE 'http://localhost:8080/api/posts/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

---

## 💬 Comments

> **Base path:** `/api/comments`
>
> 🔒 **Todas** las rutas requieren **Bearer Token** (USER o ADMIN)

| Método | Endpoint | Descripción | Auth | Status |
|--------|----------|-------------|------|--------|
| `GET` | `/api/comments/post/{postId}` | Obtener por post | ✅ | `200` |
| `GET` | `/api/comments/{id}` | Obtener por ID | ✅ | `200` |
| `POST` | `/api/comments` | Crear comentario | ✅ | `201` |
| `PUT` | `/api/comments/{id}` | Actualizar (solo dueño) | ✅ | `200` |
| `DELETE` | `/api/comments/{id}` | Eliminar soft (dueño/admin) | ✅ | `200` |

### ▶️ Crear un comentario 🔒

```bash
curl --location 'http://localhost:8080/api/comments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "content": "Excelente post, muy informativo!",
    "postId": 1
}'
```

### ▶️ Obtener comentarios por post 🔒

```bash
curl --location 'http://localhost:8080/api/comments/post/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### ▶️ Obtener comentario por ID 🔒

```bash
curl --location 'http://localhost:8080/api/comments/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### ▶️ Actualizar un comentario 🔒

```bash
curl --location --request PUT 'http://localhost:8080/api/comments/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "content": "Comentario actualizado con mas detalle"
}'
```

### ▶️ Eliminar un comentario (soft delete) 🔒

```bash
curl --location --request DELETE 'http://localhost:8080/api/comments/1' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

---

## 🛡️ Pruebas de Seguridad

> Curls para verificar que la autenticación y autorización funcionan correctamente.

### 🔴 Crear post sin token — `401 Unauthorized`

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Content-Type: application/json' \
--data '{
    "title": "Post sin autenticar",
    "content": "Esto debería fallar"
}'
```

### 🔴 Obtener posts sin token — `401 Unauthorized`

```bash
curl --location 'http://localhost:8080/api/posts'
```

### 🔴 Obtener comentarios sin token — `401 Unauthorized`

```bash
curl --location 'http://localhost:8080/api/comments/post/1'
```

### 🔴 Crear post con token inválido — `401 Unauthorized`

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer token_invalido_123' \
--data '{
    "title": "Post con token inválido",
    "content": "Esto debería fallar"
}'
```

### 🔴 Editar post de otro usuario siendo USER — `403 Forbidden`

> Usa el token de un usuario que **no** sea el autor del post.

```bash
curl --location --request PUT 'http://localhost:8080/api/posts/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN_OTRO_USUARIO>' \
--data '{
    "title": "Intento de editar post ajeno",
    "content": "Esto debería fallar si no soy el dueño ni admin"
}'
```

### 🔴 Eliminar comentario de otro usuario siendo USER — `403 Forbidden`

```bash
curl --location --request DELETE 'http://localhost:8080/api/comments/1' \
--header 'Authorization: Bearer <TOKEN_OTRO_USUARIO>'
```

### 🔴 Registrar email duplicado — `409 Conflict`

```bash
curl --location 'http://localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "email": "usuario@email.com",
    "password": "password123"
}'
```

### 🔴 Login con credenciales inválidas — `401 Unauthorized`

```bash
curl --location 'http://localhost:8080/api/auth/login' \
--header 'Content-Type: application/json' \
--data '{
    "email": "usuario@email.com",
    "password": "contraseña_incorrecta"
}'
```

---

## ⚠️ Pruebas de Validación

> Curls diseñados para verificar que las validaciones y el manejo de errores funcionan correctamente.

### 🔴 Registrar sin email — `400 Bad Request`

```bash
curl --location 'http://localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "email": "",
    "password": "password123"
}'
```

### 🔴 Registrar con contraseña corta — `400 Bad Request`

```bash
curl --location 'http://localhost:8080/api/auth/register' \
--header 'Content-Type: application/json' \
--data '{
    "email": "test@email.com",
    "password": "123"
}'
```

### 🔴 Crear post sin título — `400 Bad Request`

```bash
curl --location 'http://localhost:8080/api/posts' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "title": "",
    "content": "Contenido sin titulo"
}'
```

### 🔴 Crear comentario sin contenido — `400 Bad Request`

```bash
curl --location 'http://localhost:8080/api/comments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "content": "",
    "postId": 1
}'
```

### 🔴 Obtener post que no existe — `404 Not Found`

```bash
curl --location 'http://localhost:8080/api/posts/999' \
--header 'Authorization: Bearer <TU_TOKEN>'
```

### 🔴 Crear comentario con post inexistente — `404 Not Found`

```bash
curl --location 'http://localhost:8080/api/comments' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TU_TOKEN>' \
--data '{
    "content": "Comentario en post inexistente",
    "postId": 999
}'
```

---

## 📋 Respuestas esperadas

### ✅ Respuesta exitosa — Auth

```json
{
  "status": 201,
  "message": "Usuario registrado exitosamente",
  "data": {
    "token": "eyJhbGciOiJIUzM4NCJ9...",
    "tokenType": "Bearer",
    "expiresIn": 86400,
    "email": "usuario@email.com",
    "role": "USER"
  }
}
```

### ✅ Respuesta exitosa — Post

```json
{
  "status": 200,
  "message": "Post obtenido exitosamente",
  "data": {
    "id": 1,
    "title": "Mi primer post",
    "content": "Contenido...",
    "createdAt": "2026-03-10T10:00:00",
    "updatedAt": "2026-03-10T10:00:00",
    "authorEmail": "usuario@email.com",
    "comments": []
  }
}
```

### ❌ No autenticado (401)

```json
{
  "status": 401,
  "error": "UNAUTHORIZED",
  "message": "No estás autenticado. Proporciona un token válido.",
  "path": "/api/posts",
  "timestamp": "2026-03-10T10:00:00"
}
```

### ❌ Sin permisos (403)

```json
{
  "status": 403,
  "error": "FORBIDDEN",
  "message": "No tienes permisos para modificar este post",
  "path": "/api/posts/1",
  "timestamp": "2026-03-10T10:00:00"
}
```

### ❌ Email duplicado (409)

```json
{
  "status": 409,
  "error": "CONFLICT",
  "message": "El email ya está registrado: usuario@email.com",
  "path": "/api/auth/register",
  "timestamp": "2026-03-10T10:00:00"
}
```

### ❌ Error de validación (400)

```json
{
  "status": 400,
  "error": "VALIDATION_ERROR",
  "message": "Error de validación en los campos enviados",
  "path": "/api/auth/register",
  "timestamp": "2026-03-10T10:00:00",
  "fieldErrors": {
    "password": "La contraseña debe tener entre 8 y 100 caracteres"
  }
}
```

### ❌ Recurso no encontrado (404)

```json
{
  "status": 404,
  "error": "NOT_FOUND",
  "message": "Post no encontrado con id: 999",
  "path": "/api/posts/999",
  "timestamp": "2026-03-10T10:00:00"
}
```


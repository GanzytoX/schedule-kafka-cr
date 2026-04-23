# Kafka Retry Broker - Chain of Responsibility 🚀

Este proyecto implementa un sistema de gestión de reintentos de mensajes utilizando **Kafka**, **Spring Boot**, **PostgreSQL** y **MongoDB**, siguiendo el patrón de diseño **Chain of Responsibility** para procesar los trabajos en pasos secuenciales.

## 🏗️ Arquitectura del Sistema

El sistema sigue un flujo estricto de 4 pasos (A, B, C, D) como se detalla en el diagrama técnico:

1. **Paso A (Retry Handlers)**: Intento de comunicación con el microservicio correspondiente vía HTTP (Pagos, Órdenes o Productos).
2. **Paso B (Notification)**: Envío de notificación real por correo electrónico (`Gmail`) tras el éxito de la operación.
3. **Paso D (Final Persistence)**: Almacenamiento del resultado procesado en la nube usando **MongoDB Atlas** para histórico y auditoría.
4. **Paso C (Relational DB Update)**: Actualización final del estado del trabajo en **PostgreSQL** (marcado como `SUCCESS`) para que el Scheduler no lo vuelva a procesar.

## 🛠️ Tecnologías utilizadas

- **Java 21** & **Spring Boot 3.2**
- **Apache Kafka** (Mensajería)
- **PostgreSQL 16** (Persistencia Relacional Local - Jobs Pendientes)
- **MongoDB Atlas** (Persistencia NoSQL Cloud - Resultados Finales)
- **Maven** (Gestión de Dependencias)
- **TypeScript & Node.js** (Scripts de Prueba)

## 🚀 Guía de Inicio Rápido

### 1. Requisitos previos

- Docker y Docker Compose instalados.
- Java 21+ instalado.
- Node.js instalado (para los scripts de prueba).

### 2. Levantar Infraestructura

Desde la raíz del proyecto, levanta Kafka y PostgreSQL (MongoDB ahora está en la nube):

```bash
docker-compose up -d
```

### 3. Configurar Correo (Opcional)

Si deseas recibir notificaciones reales, edita `src/main/resources/application.properties` y coloca tu **Contraseña de Aplicación de Google**:

```properties
spring.mail.password=TU_CONTRASEÑA_DE_APLICACION_AQUI
```

### 4. Ejecutar la Aplicación

```bash
$env:JAVA_HOME = "C:\Program Files\Java\jdk-21"
mvn spring-boot:run
```

## 🧪 Pruebas del Sistema

Puedes probar el sistema de dos maneras diferentes:

### Opción A: Usando Postman (Recomendado)

Importa el archivo **`postman_collection.json`** incluido en la raíz del proyecto. Contiene los endpoints pre-configurados para enviar mensajes de Pagos, Órdenes y Productos mediante la API REST (`TestProducerController`).

### Opción B: Usando Scripts de Terminal (Bulk Test)

Ejecuta el script de prueba masiva que inyecta mensajes directamente a Kafka.

```bash
# Instalar dependencias (solo la primera vez)
npm install

# Enviar los 3 tópicos simultáneamente
npx tsx scripts/test-all.ts
```

## 📊 Verificación de Resultados

- **Logs**: Observa la consola de Spring Boot para ver el recorrido por cada paso de la cadena.
- **Correo Electrónico**: Verifica la bandeja de entrada configurada.
- **MongoDB Atlas**: Conéctate a la nube usando la URI de `application.properties` para ver la colección `processed_results`.
- **PostgreSQL**: Verifica las tablas `payment_retry_jobs`, `order_retry_jobs` y `product_retry_jobs` para confirmar el cambio de estado de `PENDING` a `SUCCESS`.

---

© 2026 - Proyecto de Arquitectura de Mensajería con Kafka

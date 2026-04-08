# Kafka Retry Broker - Chain of Responsibility 🚀

Este proyecto implementa un sistema de gestión de reintentos de mensajes utilizando **Kafka**, **Spring Boot**, **PostgreSQL** y **MongoDB**, siguiendo el patrón de diseño **Chain of Responsibility** para procesar los trabajos en pasos secuenciales.

## 🏗️ Arquitectura del Sistema

El sistema sigue un flujo de 4 pasos (A, B, C, D) como se detalla en el diagrama técnico:

1.  **Paso A (Retry Handlers)**: Intento de comunicación con el microservicio correspondiente (Pagos, Órdenes o Productos).
2.  **Paso B (Notification)**: Envío de notificación por correo electrónico tras el éxito de la operación.
3.  **Paso C (Relational DB Update)**: Actualización del estado del trabajo en **PostgreSQL** (SUCCESS/FAILED).
4.  **Paso D (Final Persistence)**: Almacenamiento del resultado procesado en **MongoDB** para histórico y auditoría.

## 🛠️ Tecnologías utilizadas

- **Java 21** & **Spring Boot 3.2**
- **Apache Kafka** (Mensajería)
- **PostgreSQL** (Persistencia Relacional - Jobs Pendientes)
- **MongoDB** (Persistencia NoSQL - Resultados Finales)
- **Maven** (Gestión de Dependencias)

## 🚀 Guía de Inicio Rápido

### 1. Requisitos previos

- Docker y Docker Compose instalados.
- Java 21+ instalado.
- Maven instalado (opcional, puedes usar tu IDE).

### 2. Levantar Infraestructura

Desde la raíz del proyecto, levanta Kafka y las bases de datos:

```bash
docker-compose up -d
```

### 3. Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

### 4. Realizar Pruebas

Contamos con scripts de prueba automatizados en la carpeta `scripts/`. Para ejecutarlos necesitas tener Node.js y pnpm instalados:

```bash
# Instalar dependencias de los scripts (solo la primera vez)
pnpm install

# Probar flujo de PAGOS
pnpm exec tsx scripts/test-send.ts

# Probar flujo de ÓRDENES
pnpm exec tsx scripts/test-order.ts
```

## 📊 Verificación de Resultados

- **Logs**: Observa la consola de Spring Boot para ver el recorrido por cada paso de la cadena.
- **MongoDB**: Conéctate a `mongodb://root:rootpassword@127.0.0.1:27017` para ver la colección `processed_results`.
- **PostgreSQL**: Verifica las tablas `payment_retry_jobs`, `order_retry_jobs` y `product_retry_jobs` para confirmar el cambio de estado a `SUCCESS`.

---

© 2026 - Proyecto de Arquitectura de Mensajería con Kafka

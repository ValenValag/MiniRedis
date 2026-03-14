# MiniRedis (Java)

MiniRedis es una implementación simplificada de un servidor tipo Redis escrita en Java.
El objetivo del proyecto no es replicar completamente Redis, sino **entender y practicar los fundamentos de los sistemas backend** construyendo un servidor desde cero.

El proyecto implementa un **servidor TCP concurrente** que permite almacenar datos en memoria usando un modelo **key–value** y soporta expiración de claves (TTL).

---

## Objetivo del proyecto

Este proyecto fue desarrollado con fines de aprendizaje y experimentación.
La meta principal es **profundizar en conceptos fundamentales de backend engineering** que normalmente quedan ocultos detrás de frameworks o servicios gestionados.

En lugar de construir una API tradicional, este proyecto explora cómo funcionan realmente los sistemas que hay **debajo de las aplicaciones backend modernas**.

---

## Características actuales

* Servidor TCP que escucha conexiones de clientes
* Procesamiento concurrente de múltiples conexiones
* Almacenamiento en memoria tipo key-value
* Comandos básicos similares a Redis
* Expiración de claves mediante TTL (time-to-live)
* Eliminación perezosa de claves expiradas (lazy expiration)

---

## Comandos soportados

SET

```
SET <key> <value> <ttl_seconds>
```

Ejemplo:

```
SET ciudad Alicante 10
```

GET

```
GET <key>
```

Ejemplo:

```
GET ciudad
```

TTL

```
TTL <key>
```

Ejemplo:

```
TTL ciudad
```

DEL

```
DEL <key>
```

Ejemplo:

```
DEL ciudad
```

---

## Ejemplo de uso

Conectar con `netcat`:

```
nc localhost 6379
```

Ejemplo de interacción:

```
SET ciudad Alicante 10
OK

GET ciudad
Alicante

-- Después de 2 segundos:
TTL ciudad
8

-- Después de 10 segundos:
GET ciudad
NULL
```

---

## Conceptos de backend explorados

Este proyecto está enfocado en practicar conceptos fundamentales de ingeniería backend:

### Networking

* sockets TCP
* gestión de conexiones cliente-servidor

### Concurrencia

* manejo de múltiples clientes usando threads
* estructuras thread-safe (`ConcurrentHashMap`)

### Protocolos simples

* parsing de comandos tipo texto
* diseño de un protocolo cliente-servidor sencillo

### Gestión de estado

* almacenamiento en memoria
* expiración de datos mediante TTL

### Sistemas backend

* arquitectura de servidores
* lifecycle de una petición

---

## Tecnologías utilizadas

* Java
* Java Sockets
* ConcurrentHashMap
* Threads

---

## Posibles mejoras

Este proyecto puede evolucionar para incluir características más avanzadas:

* limpieza activa de claves expiradas
* implementación del protocolo RESP
* pipeline de comandos
* persistencia en disco
* modelo event-loop con Java NIO
* estructuras de datos adicionales (listas, sets, etc.)


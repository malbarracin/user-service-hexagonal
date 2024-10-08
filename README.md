# user-service-hexagonal

## Descripción del Proyecto

User Services es un servicio de gestión de usuarios diseñado para facilitar la creación, actualización, eliminación y búsqueda de usuarios en un sistema de música. Este proyecto sigue el enfoque de arquitectura hexagonal, lo que permite una fácil integración con diferentes capas de la aplicación y mejora la mantenibilidad y la testabilidad del código.

## Arquitectura Hexagonal

La arquitectura hexagonal, también conocida como Ports and Adapters, es un patrón arquitectónico que separa la lógica de negocio de las preocupaciones externas. En este proyecto, se utilizan diferentes "puertos" para comunicar la lógica de negocio con las diferentes capas de la aplicación:

- **Capa de Aplicación**: Contiene los servicios que orquestan la lógica de negocio.
- **Capa de Persistencia**: Maneja el almacenamiento de usuarios a través de un repositorio.
- **Capa de Entrada/Salida**: Proporciona adaptadores para interactuar con el sistema, como controladores REST y mapeadores.
  
Esta separación permite que la lógica de negocio se mantenga independiente de la forma en que se exponen o almacenan los datos.

## Requisitos

| Tecnología         |
|--------------------|
| ![Java](https://img.shields.io/badge/Java-21-007396?logo=java&logoColor=white)               |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.3-6DB33F?logo=spring-boot&logoColor=white)        |
| ![Spring WebFlux](https://img.shields.io/badge/Spring%20WebFlux-3.3.3-6DB33F?logo=spring&logoColor=white)    |
| ![Maven](https://img.shields.io/badge/Maven-3.6.3-C71A36?logo=apache-maven&logoColor=white)              |
| ![Docker](https://img.shields.io/badge/Docker-20.10.7-2496ED?logo=docker&logoColor=white)   

## Levantar el Proyecto

1. **Clonar el Repositorio**

   ```bash
   git clone https://github.com/malbarracin/user-service-hexagonal.git
   cd user-service-hexagonal


2. **Compilar el Proyecto**

   Asegúrate de tener Maven instalado y ejecuta el siguiente comando para compilar el proyecto:
   
   ```bash
   mvn clean install

3. **Ejecutar el Proyecto**

   Puedes ejecutar el proyecto como una aplicación Spring Boot utilizando el siguiente comando:
   
   ```bash
   mvn spring-boot:run   

## Uso

### 1. Acceder a Swagger UI

   1. Abrir Swagger UI: Accede a http://localhost:8081/user-service/webjars/swagger-ui/index.html en tu navegador.

### 2. Utilizando **Postman Create a new user**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `POST`
   - **URL**: `http://localhost:8081/user-service/users`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. En el **Body**, selecciona la opción **raw** y usa **JSON** para enviar la siguiente carga de ejemplo:

   ```json
   {
    "name": "Marcelo Alejandro Albarracín",
    "email": "marceloalejandro.albarracin@gmail.com",
    "preferredGenre": ["ROCK", "JAZZ"],
    "favoriteArtist": ["The Beatles", "Miles Davis"]
    }

4. Haz clic en **Send** para enviar la solicitud.

5. Ejemplo de respuesta esperada:

   ```json
   {
        "id": "66f5adaa43cda121008c8bd9",
        "name": "Marcelo Alejandro Albarracín",
        "email": "marceloalejandro.albarracin@gmail.com",
        "preferredGenre": [
            "ROCK",
            "JAZZ"
        ],
        "favoriteArtist": [
            "The Beatles",
            "Miles Davis"
        ]
    }

### 3. Utilizando **Postman Update an existing user**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `PUT`
   - **URL**: `http://localhost:8081/user-service/users/{id}`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. En el **Body**, selecciona la opción **raw** y usa **JSON** para enviar la siguiente carga de ejemplo:

   ```json
   {
    "name": "Marcelo Albarracín",
    "email": "albarracin@gmail.com",
    "preferredGenre": ["ROCK"],
    "favoriteArtist": ["The Beatles"]
    }

4. Haz clic en **Send** para enviar la solicitud.

5. Ejemplo de respuesta esperada:

   ```json
   {
        "id": "66f5adaa43cda121008c8bd9",
        "name": "Marcelo Albarracín",
        "email": "albarracin@gmail.com",
        "preferredGenre": [
            "ROCK"
        ],
        "favoriteArtist": [
            "The Beatles"
        ]
    }


### 4. Utilizando **Postman Get a user by ID**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `GET`
   - **URL**: `http://localhost:8081/user-service/users/{id}`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. Haz clic en **Send** para enviar la solicitud.

4. Ejemplo de respuesta esperada:

   ```json
   {
        "id": "66f5adaa43cda121008c8bd9",
        "name": "Marcelo Alejandro Albarracín",
        "email": "marceloalejandro.albarracin@gmail.com",
        "preferredGenre": [
            "ROCK",
            "JAZZ"
        ],
        "favoriteArtist": [
            "The Beatles",
            "Miles Davis"
        ]
    }

### 5. Utilizando **Postman Get all users**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `GET`
   - **URL**: `http://localhost:8081/user-service/users`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. Haz clic en **Send** para enviar la solicitud.

4. Ejemplo de respuesta esperada:

   ```json
   [
        {
            "id": "66f5adaa43cda121008c8bd9",
            "name": "Marcelo Alejandro Albarracín",
            "email": "marceloalejandro.albarracin@gmail.com",
            "preferredGenre": [
                "ROCK",
                "JAZZ"
            ],
            "favoriteArtist": [
                "The Beatles",
                "Miles Davis"
            ]
        }
    ]

### 6. Utilizando **Postman Delete a user**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `DELETE`
   - **URL**: `http://localhost:8081/user-service/users/{id}`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. Haz clic en **Send** para enviar la solicitud.

4. Ejemplo de respuesta esperada:

   ```json
   204 No Content

### 7. Utilizando **Postman Update mood an existing user**

1. Abre **Postman** y crea una nueva **request** con los siguientes detalles:

   - **Method**: `PUT`
   - **URL**: `http://localhost:8081/user-service/users/{id}/mood`

2. En la sección de **Headers**, agrega lo siguiente:

   - `Content-Type`: `application/json`

3. En el **Body**, selecciona la opción **raw** y usa **JSON** para enviar la siguiente carga de ejemplo:

   ```json
   {
    "mood": "MOTIVATED"
    }

4. Haz clic en **Send** para enviar la solicitud.

5. Ejemplo de respuesta esperada:

   ```json
   {
        "id": "66f6c16114bc0440df633f97",
        "name": "Marcelo Alejandro Albarracín",
        "email": "malbarracin@gmail.com",
        "mood": "MOTIVATED",
        "preferredGenre": [
            "ROCK",
            "JAZZ"
        ],
        "favoriteArtist": [
            "The Beatles",
            "Miles Davis"
        ]
    }
    
## ¿Te gusta el contenido que comparto? Invítame un café para ayudarme a seguir creando. ¡Gracias por tu apoyo!
[![Buy Me a Coffee](https://img.shields.io/badge/Buy%20Me%20a%20Coffee-F7DF1E?style=for-the-badge&logo=buy-me-a-coffee&logoColor=black)](https://buymeacoffee.com/malbarracin)    

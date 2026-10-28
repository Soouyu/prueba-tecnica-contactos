# Prueba tecnica desarrollo
API REST para la gestion de contactos - backend (api rest)

## Tecnologias usadas
- Java 21 , spring boot 4.1.2
- Spring web, Spring Data jpa, Spring Security, Spring Validation
- Postgresql
- JWT 

## Variables de entorno
Los secretos no estan en el repo, deben definirse estas variables antes de ejecutar

| DB_PASSWORD  | JWT_SECRET | APP_USUARIO | APP_CLAVE

## Como se ejecuta?

1.-  Se debe levantar la base de datos ( esta con postgresql en el puerto 5433)
docker comopose up -d

2.-  Se debe definir las variables de entorno y ejecutar la aplicacion (puerto 8080)
En el mismo ide de intellij: poner en run > edit configuraciones > environment variables y se ejecuta 
con normalidad


## Credenciales de prueba

- Usuario: admin
-  Password: admin123

## Endpoints principales

POST /api/auth/login  |  iniciar sesion (este devuelve un token)
GET /api/contactos | listar los datos (con paginacion)
GET /api/contactos?buscar=nombre | se puede buscar por nombre 
GET /api/contactos/{id}  |  busca por id y trae un dato
POST /api/contactos | se debe enviar un json permite crear un nuevo contacto
PUT /api/contactos/{id} permite editar un contacto 
DELETE /api/contactos/{id} | permite eliminar un contacto por su id

Todas las rutas de api/contactos requieren un token en la cabecera: 
'Authorization: Bearer token'

## Arquitectura
Organizado por capas son las siguientes:
- Controladores
- Servicios
- Base de datos
- Dto
- Modelo
- Seguridad
- Excepciones

## base de datos y docker
La base de datos es potgresql y se levanta con docker
 para levantarla 
  docker compose up -d

   Esto crea la base de datos el puerto 5433

  Variables de entorno que necesita el backend para arrancar
- DB_PASSWORD=contactos123
- JWT_SECRET=una-clave-secreta-larga
- APP_USUARIO=admin
- APP_CLAVE=admin123


La aplicacion corre en el puerto 8080.

## Documentación de la API (Swagger)

La API está documentada con OpenAPI/Swagger (springdoc-openapi).

Con el backend corriendo, abrir en el navegador:

    http://localhost:8080/swagger-ui.html

## Desiciones tecnicas

- Arquitectura por capas
Con esta idea de arquitectura separe el codigo en controlador, servicio, base de datos,dto, modelo y seguridad
para que cada parte tenga una sola responsabilidad y sea mas facil de revisar
Asi el controlador no sabe de base de datos y los servicios no sabe que es HTTP,
lo que hace el codigo mas ordenado y facil de llevar.
- Dtos
Use Dtos request y responses para controlar exactamente que datos entran y salen,
de la api sin exponer directamente la entidad de la base de datos.
- Seguridad con JWT
Utilize jwt ya que permite autenticarse sin estado (stateless), el servidor 
no guarda sesiones; cada peticion viaja con su token y el filtro lo puede validar,
El login es libre y el resto de las rutas quedan protegidas.
- Secretos variables de entorno
Las claves como la de jwt, base de datos, login no estan en el repositorio, de definen por variables
de entorno que permite no exponer informacion sensible a traves de internet.
- Paginacion en el listado
Una paginacion se utiliza para no traer todos los contactos de golpe, mejorando asi el rendimiento cuando
existen muchos registros , en este caso la base no tiene tantos datos pero si en algun momento llegara eso no seria un problema
- Validacion 
Se pide siempre los datos del backend (nombre y formato de correo)
y tambien en el frontend, para dar un aviso temprano al usuario y mantener los datos consistentes.
- Postgresql
Use la base de datos relacional postgresql porque  necesitaba una estructura clara de los contactos
y sus etiquetas, que van en una tabla aparte porque un contacto puede tener muchas etiquetas y necesitaba una relacion.


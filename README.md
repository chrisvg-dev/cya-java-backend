# Cómo desplegar la aplicación con docker

Necesitas tener lo siguiente instalado.
> Docker
> 
> Docker compose (en Windows se instala con docker desktop)

La aplicación está configurada para poder ejecutarse con docker, lo único que debes hacer es lo siguiente:
1. Clonar el repositorio
2. Acceder a la carpeta desde una consola
3. Generar el .jar del proyecto con **mvn clean package** o **./mvnw clean package**
4. Lanzar la aplicación con docker-compose con el siguiente comando:: **docker-compose up --build**
5. La aplicación se lanza en el puerto 9090.


# Cómo desplegar la aplicación con maven

No hace falta descargar ni instalar ninguna dependencia, solo debes tener instalado Java.

La aplicación está configurada para poder ejecutarse con docker, lo único que debes hacer es lo siguiente:
1. Clonar el repositorio
2. Acceder a la carpeta desde una consola
3. Lanzar la aplicación con docker-compose con el siguiente comando:: **./mvnw spring-boot:run**
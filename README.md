<h1>
  <strong> CRUD de Usuarios - Arquitectura de Microservicios </strong>
  </h1>

Este proyecto implementa un sistema CRUD (Crear, Leer, Actualizar, Eliminar) para la gestión de usuarios, utilizando una arquitectura basada en microservicios y PostgreSQL como base de datos principal.

## Tabla de Contenidos
- [Requisitos Previos](#requisitos-previos)
- [Instrucciones de Compilación y Ejecución](#instrucciones-de-compilación-y-ejecución)
- [Configuración de la Base de Datos PostgreSQL](#configuración-de-la-base-de-datos-postgresql)
- [Descripción de la Arquitectura](#descripción-de-la-arquitectura)
- [Ejecución de Pruebas Unitarias](#ejecución-de-pruebas-unitarias)

## Requisitos Previos
<details>
  <summary>Ver Requisitos Previos</summary>

  ### Requisitos Previos
  Antes de comenzar, asegúrate de tener instalados los siguientes componentes:

    - JDK 11 o superior
    - Maven 3.6.0 o superior
    - Docker (opcional, para ejecutar la base de datos PostgreSQL en un contenedor)
    - PostgreSQL 12 o superior
</details>

## Instrucciones de Compilación y Ejecución
<details>
  <summary>Ver Instrucciones de Compilación y Ejecución</summary>

  ### Instrucciones de Compilación y Ejecución
  Sigue estos pasos para compilar y ejecutar la aplicación:
  
    ## 1. Clonar el repositorio:
    - git clone https://github.com/4lejandroquintero/crud-usuarios-microserviciostres.git
    - cd crud-usuarios-microserviciostres

    ## 2. Clonar el repositorio:
    - mvn clean install

    ## 3. Configurar y ejecutar la base de datos PostgreSQL
    - Instalar PostgreSQL localmente, descarga e instala la versión adecuada para tu sistema operativo desde la página oficial. Luego, crea una base de datos y un usuario con los permisos necesarios.
      
     ## 4. Configurar las variables de entorno
    - La aplicación utiliza variables de entorno para la configuración de la base de datos. Asegúrate de establecer las siguientes variables antes de ejecutar la aplicación:
        export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/microrobot-microservicio
        export SPRING_DATASOURCE_USERNAME=usuario
        export SPRING_DATASOURCE_PASSWORD=contraseña
</details>

## Configuración de la Base de Datos PostgreSQL
<details>
  <summary>Ver Configuración de la Base de Datos PostgreSQL</summary>

  ### Configuración de la Base de Datos PostgreSQL
  La aplicación requiere una base de datos PostgreSQL configurada con las siguientes características:

      - Nombre de la base de datos: microrobot-microservicio
      - Usuario: usuario
      - Contraseña: contraseña
      
En los archivos application.yml de los microservicios User y Task se encuentran estas configuraciones de la BD.
</details>

## Descripción de la Arquitectura
<details>
  <summary>Ver Descripción de la Arquitectura</summary>

  ### Descripción de la Arquitectura
  La aplicación está diseñada siguiendo una arquitectura de microservicios, donde cada componente es independiente y se comunica con los demás a través de interfaces bien definidas. Esto permite una mayor escalabilidad, mantenibilidad y resiliencia en el sistema.

En esta arquitectura, los servicios individuales manejan funcionalidades específicas y se comunican entre sí mediante solicitudes HTTP RESTful. Para facilitar esta interacción y brindar una experiencia unificada, se utiliza un API Gateway, que centraliza todas las peticiones hacia los microservicios y expone una única interfaz para los clientes.

### Microservicios Principales
### 1. Servicio de Usuarios (User Service)
Gestiona todas las operaciones relacionadas con los usuarios del sistema, incluyendo:

      - Registro de usuarios
      - Consulta de perfiles
      - Actualización de datos
      - Eliminación de cuentas
      - Relación de usuarios con tareas
      
Este servicio interactúa directamente con la base de datos PostgreSQL para almacenar y recuperar información.

### 2. Servicio de Tareas (Task Service)
Se encarga de gestionar las tareas asociadas a los usuarios y proporciona funcionalidades como:

      - Creación de tareas
      - Asignación de tareas a usuarios
      - Actualización del estado de tareas
      - Eliminación de tareas
      
También interactúa con PostgreSQL, permitiendo la persistencia de las tareas y su relación con los usuarios.

### 3. API Gateway (Gateway Service)
El API Gateway es un componente clave en la arquitectura de microservicios, ya que centraliza las solicitudes de los clientes y las redirige al microservicio correspondiente.

### Funciones principales del Gateway:
✅ <strong>Unificar Endpoints:</strong>  En lugar de exponer múltiples URLs para cada microservicio, el API Gateway proporciona una única dirección para todas las peticiones.
✅ <strong>Balanceo de Carga:</strong> Puede distribuir el tráfico entre múltiples instancias de un microservicio si hay escalado horizontal.
✅ <strong>Seguridad y Autenticación:</strong> Puede gestionar autenticación con JWT y validar accesos antes de reenviar las solicitudes.
✅ <strong>Manejo de Errores y Timeouts:</strong> Si un microservicio falla o se ralentiza, el API Gateway puede gestionar respuestas adecuadas.
✅ <strong>Redirección de Rutas:</strong> Define reglas de enrutamiento para que las peticiones sean enviadas al microservicio correcto.

Ejemplo de cómo el Gateway expone un solo punto de acceso:

Servicio	Endpoint Directo	Endpoint a través del API Gateway

      - User Service	/user	/api/v3/user
      - Task Service	/tasks	/api/v3/tasks
      
El cliente solo interactúa con /api/, mientras que el API Gateway reenvía las solicitudes al microservicio correspondiente.

### Comunicación entre Microservicios
La comunicación entre los microservicios se realiza mediante llamadas HTTP RESTful, utilizando el API Gateway como intermediario.

1️⃣ Un cliente envía una solicitud a http://localhost:8080/api/v3/tasks
2️⃣ El API Gateway redirige la petición al Task Service en http://localhost:9090/v3/tasks
3️⃣ El Task Service procesa la solicitud y devuelve la respuesta al Gateway
4️⃣ El API Gateway responde al cliente con los datos obtenidos

Esta estrategia facilita el desarrollo, ya que los clientes solo interactúan con un punto de entrada común, sin necesidad de conocer las direcciones individuales de cada microservicio.

### Seguridad y Autenticación
La aplicación implementa seguridad basada en tokens JWT, de la siguiente manera:
🔐 El usuario inicia sesión en el User Service y recibe un token JWT
🔐 Para cada petición posterior, el token JWT se envía en los headers
🔐 El API Gateway verifica el token y solo permite solicitudes válidas
🔐 Los microservicios pueden validar el token antes de procesar la solicitud

Esto garantiza que solo usuarios autenticados puedan acceder a ciertos recursos del sistema.
</details>

## Ejecución de Pruebas Unitarias
<details>
  <summary>Ver Ejecución de Pruebas Unitarias</summary>

  ### Ejecución de Pruebas Unitarias
  Para garantizar la calidad y funcionalidad del código, se han implementado pruebas unitarias utilizando JUnit y Mockito.

  Para ejecutar las pruebas unitarias, sigue estos pasos:

      - Compilar la aplicación y ejecutar las pruebas:

          - mvn clean test
          
Este comando compilará el código y ejecutará todas las pruebas unitarias. Los resultados de las pruebas se mostrarán en la consola y se generarán informes en el directorio <strong>target/surefire-reports.</strong>

### Verificar la cobertura de pruebas:

Para verificar la cobertura de las pruebas, puedes utilizar herramientas como JaCoCo. Ejecuta el siguiente comando para generar un informe de cobertura:

          - mvn jacoco:report
El informe se generará en target/site/jacoco/index.html y podrás abrirlo en tu navegador para revisar qué partes del código están cubiertas por las pruebas.
</details>




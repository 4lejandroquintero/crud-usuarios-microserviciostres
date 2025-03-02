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
  Aquí va el contenido de la configuración de la base de datos...
</details>

## Descripción de la Arquitectura
<details>
  <summary>Ver Descripción de la Arquitectura</summary>

  ### Descripción de la Arquitectura
  Aquí va el contenido de la descripción de la arquitectura...
</details>

## Ejecución de Pruebas Unitarias
<details>
  <summary>Ver Ejecución de Pruebas Unitarias</summary>

  ### Ejecución de Pruebas Unitarias
  Aquí va el contenido de la ejecución de pruebas unitarias...
</details>




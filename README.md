# testNttData
prueba tecnica Ntt Data

La siguiente aplicacion consta de la funcionalidad de inventario de equipos

    CRUD de equipos

Para levantar el proyecto

clonar proyecto git clone https://github.com/relativismofisico/testNttData

Abrir proyecto con el IDE de preferencia y configurar las credenciales de bases de datos en el archivo applicatioon-local.properties

Los ENDPOINT disponibles son:

Permite crear un objeto equipo 
    http://localhost:8090/api/equipment/create
Permite Listar todos los equipos disponibles
    http://localhost:8090/api/equipment/findAll
Permite buscar un equipo mediante su codigo de serie
    http://localhost:8090/api/equipment/findByCode/{serialCodeEquipment]
Permite actualizar la informacion de un equipo mediante su codigo de serie
    http://localhost:8090/api/equipment/upDate/{serialCodeEquipment]
Permite eliminar la informacion de un equipo mediante su codigo de serie
    http://localhost:8090/api/equipment//delete/{serialCodeEquipment}
    
Archivo de base de datos
De forma local se utiliza una base de datos en memoria que la api por medio de Spring boot se encarga de gestionar, para produccion las tablas 
que se utilizan para el proyecto se encuentran en resources/db.migration/V1.1__create_table.sql



    

Compilado y ejecución Mercadonia
=========================================
This relies on the DataNucleus Maven plugin.

1. Ejecutar comandos de la DB del fichero src/main/resources/database.sql
	Crea la DB y el el Usuario spq y hace un Grant de la DB al usuario
	
2. Ejecutar: "mvn clean compile" 
	Contruye el proyecto

3. Ejecutar: "mvn datanucleus:schema-create"
	Crea el esquema de la DB

4. Ejecutar: "mvn exec:java -P datos"
   Introduce los datos en la DB

5. Ejecutar: "mvn test"
	Comprueba los tests unitarios del proyecto 

6. Ejecutar la VentanaLoging.java
	Proyecto funcional


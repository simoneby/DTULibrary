# DTULibrary
Git link is https://github.com/SEGroupE/DTULibrary.git

Requirements:

MySQL database
JDK 10 or higher

Installation:

The application requires a database running, therefor create a database.

Default app values for the database:
name: springbootdb
user: root
password: root

If any values differs from this DTULibrary\Mazemap\src\main\resources\static\application.properties need to reflect the actual values database name, user and password.

The webapp is a maven project using springboot, packaging as a war file to deploy directly to a Tomcat server. 
However for local use you do not need to create a Tomcat server, springboot will create one, when running.

The maven command for running the app is mvn spring-boot:run or mvn clean spring-boot:run to create a clean install.
using eclipse for instance and running as a maven project the goal of the run configuration should be spring-boot:run
The base directory for the project is ${workspace_loc://DTULibrary/Mazemap}

Feel free to ask for further assistance with the webapp.




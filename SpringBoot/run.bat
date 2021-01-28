:: Runs the Spring Boot Application Server
@ECHO OFF
:: choose gradle or maven
SET /p choice= "Gradle or Maven. Type g or m to indicate your choice..."
IF "%choice%" == "g" ( CALL gradle bootRun) ELSE ( CALL mvn spring-boot:run)

@ECHO OFF
:: invoke gradle fresh build on Java
CALL Java/build
:: invoke JAR production build
CALL gradle clean build
:: execute JAR
CALL java -jar Java/build/libs/Java-0.0.2-SNAPSHOT.jar
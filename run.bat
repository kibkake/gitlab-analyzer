@ECHO OFF
:: invoke gradle fresh build
CALL gradle clean build
:: execute JAR
CALL java -jar Java/build/libs/Java-0.0.2-SNAPSHOT.jar
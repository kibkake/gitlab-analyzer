@ECHO OFF
:: clean build
CALL gradle clean
:: run the unit tests
@ECHO:
ECHO "Running Tests..."
CALL gradle test
:: run the main application
@ECHO:
ECHO "Running Application: Main.class ..."
CALL gradle run
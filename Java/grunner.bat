@ECHO OFF
:: clean build
CALL gradle clean
:: run the main application
@ECHO:
ECHO "Running Application: Main.class ..."
CALL gradle bootRun
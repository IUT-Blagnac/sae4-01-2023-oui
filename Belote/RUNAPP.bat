@echo off

:: Maven package
call mvn clean install compile package

:: Run the app
java -jar ./target/belote-1.0-jar-with-dependencies.jar
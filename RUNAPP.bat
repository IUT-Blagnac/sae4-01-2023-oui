@echo off

:: Go to the directory where app is located
cd ./belote

:: Maven package
call mvn clean install compile package

:: Run the app
java -jar ./target/belote-1.0-jar-with-dependencies.jar
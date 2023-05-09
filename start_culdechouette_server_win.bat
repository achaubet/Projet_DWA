@echo off
set JRE_HOME=C:\Program Files\Java\jdk1.8.0_341
set CATALINA_HOME=C:\Users\Arnaud\Documents\apache-tomcat-9.0.74
set PATH=%PATH%;%CATALINA_HOME%\bin
set WEBAPP_URL="http://localhost:8080/ProjetCulDeChouette-1.0/PageAccueil.jsp"
:: Copie de l'application war vers le dossier de déploiement de Tomcat
copy "%cd%\target\ProjetCulDeChouette-1.0.war" "%CATALINA_HOME%\webapps"
:: Démarrage de l'application Web
start "Cul de Chouette Web Server" "%CATALINA_HOME%\bin\catalina.bat" run
:: Remplacer par un autre navigateur si souhaité
start iexplore.exe %WEBAPP_URL%

#!/bin/bash
# Utiliser la version 9 de Tomcat
export CATALINA_HOME=/home/arnaud/apache-tomcat-9.0.74
export PATH=$CATALINA_HOME/bin:$PATH
# Permet de recompiler proprement le projet (mettre en commentaire si non souhaité)
mvn clean install
# Copie du fichier war vers le dossier de déploiement de tomcat
cp target/ProjetCulDeChouette-1.0.war $CATALINA_HOME/webapps/
# Démarrage de Tomcat Catalina
$CATALINA_HOME/bin/catalina.sh run

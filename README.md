# Projet Dev Web Avancé

## Objectif du Projet

L'objectif de ce projet était de développer un petit jeu en multijoueur en utilisant certaines technologies présentes dans Java EE (Jakarta).

## Technologies / API

Dans le cadre de ce projet, nous devions obligatoirement utiliser certaines technologies présentes dans Java EE. Cependant, l'utilisation d'autres technologies, en complément de celles imposées, était autorisé.

### Technologies obligatoires

* JPA

* JSP et Servlets

### Technologies facultatives utilisées

* WebSockets

* AJAX/jQuery

## Lancement

Pour pouvoir lancer le projet, il faut préalablement avoir installé [Apache Tomcat 9](https://tomcat.apache.org/download-90.cgi)

### Linux

Pour les systèmes basés sur Linux, il faut d'abord modifier le fichier start_culdechouette_server.sh et modifier le chemin où se trouve Apache Tomcat :

``` export CATALINA_HOME=<chemin_vers_tomcat> ```

Ensuite, il faut donner les droits d'exécution :

``` chmod 700 start_culdechouette_server.sh ```

Et enfin le script peut être lancé:

``` ./start_culdechouette_server.sh ```

### Windows

Sous Windows, il faut modifier le fichier start_culdechouette_server_win.bat et mettre les bons chemins pour ``` JRE_HOME ``` qui correspond au chemin où est installé Java ainsi que pour ``` CATALINA_HOME ``` qui correspond au chemin où se trouve Apache Tomcat

``` 
set JRE_HOME=<chemin_vers_java>
set CATALINA_HOME=<chemin_vers_tomcat>
```

## Auteurs

[Tristan Taupiac](https://github.com/TristanTcDev) et [Arnaud Chaubet](https://github.com/achaubet)

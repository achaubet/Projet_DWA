<%-- 
    Document   : PageAccueil
    Created on : 27 avr. 2023, 11:06:29
    Author     : achaubet
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Jeux du Cul de Chouette</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
<body class="bg-gray-100">
    <div class="flex flex-col justify-center h-screen">
        <h1 class="text-4xl font-bold text-center mb-8">Cul de Chouette</h1>
        <div class="flex justify-center my-2">
            <a href="CreerCompte.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                Créer un compte
            </a>
            <a href="ConnexionCompte.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                Se connecter
            </a>
            <a href="Statistiques.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                Afficher les statistiques
            </a>
        </div>
    </div>
</body>
</html>


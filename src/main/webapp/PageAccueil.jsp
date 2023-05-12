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
        <h1 class="text-4xl font-bold text-center mt-0 mb-10">Cul de Chouette</h1>
        <%-- Check if user is logged in --%>
        <% String username = null;
           Cookie[] cookies = request.getCookies();
           if (cookies != null) {
              for (Cookie cookie : cookies) {
                 if (cookie.getName().equals("user")) {
                    username = cookie.getValue();
                    break;
                 }
              }
           }
        %>
        <%-- If user is logged in, display their username --%>
        <% if (username != null) { %>
            <h1 class="text-2xl font-bold text-center mt-10">Connecté en tant que: <%= username %></h1>
        <% } %>
        <%-- Display links based on whether user is logged in or not --%>
        <div class="flex justify-center my-2">
            <% if (username == null) { %>
                <a href="CreerCompte.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                    Créer un compte
                </a>
                <a href="ConnexionCompte.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                    Se connecter
                </a>
            <% } else { %>
                <a href="Lobby.jsp" id="lobby" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                    Rejoindre le Lobby
                </a>
                <a href="ModifierCompte.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                    Modifier mes informations
                </a>
            <% } %>
            <a href="Statistiques.jsp" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">
                Afficher les statistiques
            </a>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <% if (username != null) { %>
    <script>
        setInterval(function() {
            console.log("?");
            $.ajax({
                url: 'ServletPartieDemarre',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    if(response.hasStarted) {
                        document.getElementById("lobby").hidden = true;
                    } else {
                        document.getElementById("lobby").hidden = false;
                    }
                }
            });
        }, 2000);
    </script>
    <% } %>
</body>
</html>

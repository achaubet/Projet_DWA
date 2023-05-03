<%-- 
    Document   : Lobby
    Created on : 1 mai 2023, 14:57:41
    Author     : Arnaud
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
        <% String username = null;
           int userID = 0;
           Cookie[] cookies = request.getCookies();
           if (cookies != null) {
              for (Cookie cookie : cookies) {
                 if (cookie.getName().equals("user")) {
                    username = cookie.getValue();
                 }
                 if (cookie.getName().equals("id")) {
                    userID = Integer.parseInt(cookie.getValue());
                 }
              }
           }
           if (username == null) {
              response.sendRedirect("PageAccueil.jsp");
            }
        %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lobby</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src=" https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.all.min.js "></script>
    <link href=" https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.min.css " rel="stylesheet">
    <link href=" ${pageContext.request.contextPath}/css/lobby.css" type="text/css" rel="stylesheet">
</head>
    <body class="bg-gray-100">
    <div class="flex flex-col justify-center h-screen">
        <h1 class="text-4xl font-bold text-center mt-0 mb-10">Lobby</h1>
        <div class="list-container">
            <h2>Choix des joueurs :</h2>
            <ul id="user-list" class="bg-white rounded-lg shadow-md p-4 mb-4"></ul>
        </div>
        <div class="list-container"  id="user-list-confirmed-global" hidden>
            <h2>Ordre de la liste de passage des joueurs :</h2>
            <ul id="user-list-confirmed" class="bg-white rounded-lg shadow-md p-4 mb-4"></ul>
        </div>
        <div class="flex justify-center">
            <button id="send-invitations-btn" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">Send Invitations</button>
        </div>
        <button id="start-party-btn" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline" hidden>Start Party</button>
    </div>
    </body>
    <script>
        const user = {
            username: '<%=username %>',
            userID: '<%=userID %>'
        };
        // Récupérer la référence du bouton "Start Party"
        const startPartyBtn = document.getElementById("start-party-btn");

        // Ajouter un événement d'écouteur de clic au bouton
        startPartyBtn.addEventListener("click", (event) => {
            // Envoyer un message WebSocket à tous les utilisateurs connectés pour qu'ils redirigent leur page vers "Game.jsp"
            socket.send(JSON.stringify({ message: "redirectToGame" }));
        });

    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/LobbyWS.js"></script>
</html>

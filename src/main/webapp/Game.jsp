<%-- 
    Document   : Game
    Created on : 3 mai 2023, 11:56:19
    Author     : ttaupiac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String username = null;
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
        <link href=" ${pageContext.request.contextPath}/css/game.css" type="text/css" rel="stylesheet">
    </head>
    <body>
        <div class="flex justify-center items-center h-screen flex-col">
            <div class="scoreboard">
                <h2>Scoreboard</h2>
                <ul id="scoreboard-list">
                </ul>
            </div>
            
            <div class="flex justify-center items-center mt-auto">
                <button id="roll-dice-btn" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                    Lancer les Dés
                </button>
            </div>

            <div class="current-player mt-auto">
                <p class="text-center">
                    C'est le tour de:
                    <p id="actualPlayer" class="text-2xl font-bold"></p>
                </p>
            </div>

            <div class="dice" id="dice-number"></div>
            <div class="dice" id="dice-number2"></div>
        </div>
        <script>
            const user = {
                username: '<%= username%>',
                userID: '<%= userID%>'
            };
        </script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/GameWS.js"></script>
    </body>
</html>

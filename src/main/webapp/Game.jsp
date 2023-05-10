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
    <style>
        .scoreboard {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #f5f5f5;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .current-player {
            position: absolute;
            bottom: 10px;
            right: 10px;
            font-size: 18px;
        }

        .dice {
            position: absolute;
            top: 50px;
            right: 10px;
            font-size: 48px;
        }
    </style>
</head>
<body>
<div class="flex justify-center items-center h-screen flex-col">
    <button id="roll-dice-btn" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
        Lancer les Dés
    </button>

    <div class="scoreboard">
        <h2>Scoreboard</h2>
        <ul>
            <li>Player 1: 0</li>
            <li>Player 2: 0</li>
            <li>Player 3: 343</li>
        </ul>
    </div>

    <div class="current-player mt-auto">
        <p class="text-center">
            C'est le tour de: <%= username %>
        </p>
    </div>
    </div>
    <div class="dice" id="dice-number"></div>



    <div class="dice" id="dice-number"></div>
    <script>
        const user = {
            username: '<%= username %>',
            userID: '<%= userID %>'
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/GameWS.js"></script>
</body>
</html>

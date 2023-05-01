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
        %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lobby</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
    <body class="bg-gray-100">
    <div class="flex flex-col justify-center h-screen">
        <h1 class="text-4xl font-bold text-center mt-0 mb-10">Lobby</h1>
        <ul id="user-list"></ul>
        <button id="start-party-btn" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded mx-2 focus:outline-none focus:shadow-outline">Start Party</button>
    </div>
    </body>
    <script>
        console.log("COUCOU!");
        const socket = new WebSocket("ws://localhost:8080/ProjetCulDeChouette/LobbyWS");
        
        const user = {
            username: '<%=username %>',
            userID: <%=userID %>
        };

        socket.addEventListener("open", (event) => {
          socket.send(JSON.stringify(user));
        });
        
        socket.addEventListener("message", (event) => {
          console.log("Message from server: ", event.data);
        });
    </script>
</html>

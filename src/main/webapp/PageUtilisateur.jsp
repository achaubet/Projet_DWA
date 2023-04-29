<%-- 
    Document   : PageUtilisateur
    Created on : 28 avr. 2023, 08:57:41
    Author     : Arnaud
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Retrieve the username from the cookie --%>
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
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User page</title>
    <!-- Inclure les fichiers CSS de Tailwind -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
<body>
    <div class="container mx-auto">
        <h1 class="text-2xl font-bold text-center mt-6">Welcome <%= username %>!</h1>
    </div>
</body>
</html>

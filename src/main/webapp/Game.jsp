<%-- 
    Document   : Game
    Created on : 3 mai 2023, 11:56:19
    Author     : ttaupiac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
    </body>
    <script>
        const user = {
            username: '<%=username %>',
            userID: '<%=userID %>'
        };
    </script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/GameWS.js"></script>
</html>

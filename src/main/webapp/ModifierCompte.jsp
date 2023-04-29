<%-- 
    Document   : ModifierCompte
    Created on : 29 avr. 2023, 22:35:34
    Author     : Arnaud
--%>

<%@page import="CulDeChouetteDAO.IJoueur"%>
<%@page import="CulDeChouetteDAO.AbstractDAOFactory"%>
<%@page import="CulDeChouetteDAO.AbstractDAOFactory"%>
<%@page import="CulDeChouetteDAO.PersistenceKind"%>
<%@page import="CulDeChouetteDAO.CulDeChouetteDAOFactory"%>
<%@page import="POJO.Joueur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier le profil</title>
    <!-- Inclure les fichiers CSS de Tailwind -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
<body>
    <div class="container mx-auto">
        <h1 class="text-2xl font-bold text-center mt-6">Modifier le profil</h1>
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
           CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
           IJoueur daoJoueur = factory.getDAOJoueur();
           Joueur joueur = null;
           if ((userID != 0) && (username != null)) {
               joueur = daoJoueur.rechercherJoueurParId(userID);
           }
        %>
        <% if (joueur != null) { %>
        <form method="post" action="ServletModifierProfil" class="max-w-md mx-auto mt-6">
            <div class="mb-4">
                <label for="pseudo" class="block text-gray-700 font-bold mb-2">Pseudo</label>
                <input type="text" name="pseudo" id="pseudo" value="<%=joueur.getPseudo()%>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" readonly>
            </div>
            <div class="mb-4">
                <label for="mdp" class="block text-gray-700 font-bold mb-2">Mot de passe</label>
                <input type="password" name="mdp" id="mdp" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="mb-4">
                <label for="age" class="block text-gray-700 font-bold mb-2">Âge</label>
                <input type="number" name="age" id="age" value="<%=joueur.getAge()%>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="mb-4">
                <label for="sexe" class="block text-gray-700 font-bold mb-2">Genre</label>
                <select name="sexe" id="sexe" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
                    <option value="">Choisir le genre</option>
                    <option value="H" <%= joueur.getSexe() == 'H' ? "selected" : "" %>>Homme</option>
                    <option value="F" <%= joueur.getSexe() == 'F' ? "selected" : "" %>>Femme</option>
                    <option value="A" <%= joueur.getSexe() == 'A' ? "selected" : "" %>>Autre...</option>
                </select>
            </div>
            <div class="mb-4">
                <label for="ville" class="block text-gray-700 font-bold mb-2">Ville</label>
                <input type="text" name="ville" id="ville" value="<%=joueur.getVille()%>" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required>
            </div>
            <div class="flex items-center justify-between mt-6">
                <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">Modifier le profil</button>
                <a href="javascript:history.back()" class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800">Annuler</a>
            </div>
        </form>
        <% } else { %>
            <p class="text-red-500 text-center mt-6">Vous devez vous connecter pour accéder à cette page</p>
        <% } %>
    </div>
</body>
</html>
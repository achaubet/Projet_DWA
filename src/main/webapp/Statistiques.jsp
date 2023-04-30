<%-- 
    Document   : Statistique
    Created on : 27 avr. 2023, 12:29:49
    Author     : achaubet
--%>

<%@page import="POJO.Partie"%>
<%@page import="CulDeChouetteDAO.IPartie"%>
<%@page import="CulDeChouetteDAO.IJoueursPartie"%>
<%@page import="POJO.JoueursPartie"%>
<%@page import="CulDeChouetteDAO.AbstractDAOFactory"%>
<%@page import="CulDeChouetteDAO.IJoueur"%>
<%@page import="CulDeChouetteDAO.PersistenceKind"%>
<%@page import="CulDeChouetteDAO.CulDeChouetteDAOFactory"%>
<%@page import="POJO.Joueur"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Jeux du Cul de Chouette</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.19/tailwind.min.css">
</head>
<body class="bg-gray-100">
    <div class="flex flex-col justify-top h-screen mt-20">
        <h1 class="text-4xl font-bold text-center mb-8">Cul de Chouette</h1>
        <h1 class="text-4xl font-bold text-center mb-8">Statistiques par Joueurs: </h1>
        <table class="table-auto mx-auto">
            <thead>
                <tr>
                    <th class="px-4 py-2">Pseudo</th>
                    <th class="px-4 py-2">Ville</th>
                    <th class="px-4 py-2">Age</th>
                    <th class="px-4 py-2">Genre</th>
                    <th class="px-4 py-2">Nb Parties Jouées</th>
                    <th class="px-4 py-2">Nb Victoires</th>
                    <th class="px-4 py-2">Nb Victoires moyen</th>
                    <th class="px-4 py-2">Score Moyen</th>
                    <th class="px-4 py-2">Moyenne Suites gagnées</th>
                    <th class="px-4 py-2">Moyenne Chouettes velues perdues</th>
                </tr>
            </thead>
            <tbody>
                <%-- Récupération de tous les joueurs dans la base de données --%>
                <% 
                    CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
                    IJoueur daoJoueur = factory.getDAOJoueur();
                    List<Joueur> joueurs = daoJoueur.rechercherTousLesJoueurs();
                    for(Joueur joueur : joueurs) {
                %>
                <tr>
                    <td class="border px-4 py-2"><%= joueur.getPseudo() %></td>
                    <td class="border px-4 py-2"><%= joueur.getVille() %></td>
                    <td class="border px-4 py-2"><%= joueur.getAge() %></td>
                    <td class="border px-4 py-2"><%= joueur.getSexe() %></td>
                    <td class="border px-4 py-2"><%= joueur.getJoueursPartieList().size() %></td>
                    <td class="border px-4 py-2"><%= joueur.getNbVictoires() %></td>
                    <td class="border px-4 py-2"><%= joueur.getNbMoyenVictoires() %></td>
                    <td class="border px-4 py-2"><%= joueur.getScoreMoyen() %></td>
                    <td class="border px-4 py-2"><%= joueur.getMoySuitesGagnees() %></td>
                    <td class="border px-4 py-2"><%= joueur.getMoyCvPerdues() %></td>
                </tr>
                <% } %>
            </tbody>
        </table>
<h1 class="text-4xl font-bold text-center mb-8">Statistiques par Parties: </h1>
<table class="table-auto mx-auto">
    <thead>
        <tr>
            <th class="px-4 py-2">Pseudo</th>
            <th class="px-4 py-2">Score</th>
            <th class="px-4 py-2">Suites gagnées</th>
            <th class="px-4 py-2">Chouettes velues perdues</th>
        </tr>
    </thead>
    <tbody>
        <%-- Récupération de toutes les parties dans la base de données --%>
        <% 
            IPartie daoPartie = factory.getDAOPartie();
            IJoueursPartie daoJoueursPartie = factory.getDAOJoueursPartie();
            List<Partie> parties = daoPartie.rechercherToutesLesParties();
            for(Partie partie : parties) {
        %>
            <h1 class="text-4xl font-bold text-center mb-8">Partie N°<%= partie.getCodePartie() %></h1>
        <%
                List<JoueursPartie> joueursPartie = daoJoueursPartie.rechercherJoueursPartieParCodePartie(partie.getCodePartie());
                for(JoueursPartie joueurP : joueursPartie) {
                    Joueur joueur = daoJoueur.rechercherJoueurParId(joueurP.getJoueursPartiePK().getCodeJoueur());
        %>
        <tr>
            <td class="border px-4 py-2"><%= joueur.getPseudo() %></td>
            <td class="border px-4 py-2"><%= joueurP.getScore() %></td>
            <td class="border px-4 py-2"><%= joueurP.getSuiteGagnees() %></td>
            <td class="border px-4 py-2"><%= joueurP.getCvPerdues() %></td>
        </tr>
        <% 
                }
            }
        %>
    </tbody>
</table>

    </div>
</body>
</html>

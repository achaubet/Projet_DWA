/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.projetculdechouette;

import CulDeChouetteDAO.AbstractDAOFactory;
import CulDeChouetteDAO.CulDeChouetteDAOFactory;
import CulDeChouetteDAO.DAOException;
import CulDeChouetteDAO.IJoueur;
import CulDeChouetteDAO.PersistenceKind;
import POJO.Joueur;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arnaud
 */
@WebServlet(name = "ServletModifierProfil", urlPatterns = {"/ServletModifierProfil"})
public class ServletModifierProfil extends HttpServlet {

    IJoueur daoJoueur = null;
    
    @Override
    public void init() throws ServletException {
        try {
            super.init();
            // Créer une instance de Personne et l'initialiser avec les données nécessaires
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JPA);
            daoJoueur = factory.getDAOJoueur();
        } catch (DAOException ex) {
            Logger.getLogger(ServletConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request);
        System.out.println("GET");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println(request);
            System.out.println("POST");
            System.out.println(request.getParameter("pseudo"));
            String pseudo = request.getParameter("pseudo");
            String mdp = request.getParameter("mdp");
            String age = request.getParameter("age");
            String genre = request.getParameter("sexe");
            String ville = request.getParameter("ville");
            Joueur j = daoJoueur.rechercherJoueurParPseudo(pseudo);
            j.setAge(Integer.parseInt(age));
            j.setMdp(mdp);
            j.setVille(ville);
            j.setSexe(genre.toCharArray()[0]);
            daoJoueur.modifierJoueur(j);
        } catch (DAOException ex) {
            Logger.getLogger(ServletModifierProfil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            response.sendRedirect("PageAccueil.jsp");
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package CulDeChouetteServlets;

import CulDeChouetteDAO.AbstractDAOFactory;
import CulDeChouetteDAO.CulDeChouetteDAOFactory;
import CulDeChouetteDAO.DAOException;
import CulDeChouetteDAO.IJoueur;
import CulDeChouetteDAO.PersistenceKind;
import POJO.Joueur;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author achaubet
 */
@WebServlet(name = "ServletConnexion", urlPatterns = {"/ServletConnexion"})
public class ServletConnexion extends HttpServlet {
    
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupérer les valeurs saisies dans le formulaire
        String pseudo = request.getParameter("pseudo");
        String mdp = request.getParameter("mdp");
        Joueur j = daoJoueur.connexionJoueur(pseudo, mdp);
        if(j == null) {
            System.out.println("Erreur de connexion");
            String script = "Swal.fire({title: 'Erreur', icon: 'error', text: '\"Mot de passe ou nom utilisateur incorrect\"'}).then(function(result) { window.location.href = 'ConnexionCompte.jsp'; });";
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script src=\" https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.all.min.js \"></script>");
            response.getWriter().write("<link href=\" https://cdn.jsdelivr.net/npm/sweetalert2@11.7.3/dist/sweetalert2.min.css \" rel=\"stylesheet\">");
            response.getWriter().write("<script>" +
                "window.addEventListener('load', function() {" +
                    script +
                "});" +
            "</script>");
        } else {
            System.out.println("Connexion OK!");
            Cookie cookie = new Cookie("user", j.getPseudo());
            Cookie cookieId = new Cookie("id", Integer.toString(j.getCodeJoueur()));
            response.addCookie(cookie);
            response.addCookie(cookieId);
            response.sendRedirect("PageAccueil.jsp");
        } 
    }


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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

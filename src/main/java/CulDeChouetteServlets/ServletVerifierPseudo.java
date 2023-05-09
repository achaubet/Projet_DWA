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
import java.io.PrintWriter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Arnaud
 */
@WebServlet(name = "ServletVerifierPseudo", urlPatterns = {"/ServletVerifierPseudo"})
public class ServletVerifierPseudo extends HttpServlet {
    
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
        String pseudo = request.getParameter("pseudo");
        System.out.println(pseudo);
        try {
            boolean exist = daoJoueur.rechercherPseudoExistant(pseudo);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            JsonObject responseObject = javax.json.Json.createObjectBuilder()
                .add("exist", exist)
                .build();
            String responseStr = responseObject.toString();
            response.getWriter().write(responseStr);
        } catch (DAOException ex) {
            response.sendError(0, "Erreur de base de données");
        }
    }
}

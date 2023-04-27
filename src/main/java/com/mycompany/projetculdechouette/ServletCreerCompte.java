/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import CulDeChouetteDAO.AbstractDAOFactory;
import CulDeChouetteDAO.CulDeChouetteDAOFactory;
import CulDeChouetteDAO.DAOException;
import CulDeChouetteDAO.IJoueur;
import CulDeChouetteDAO.PersistenceKind;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import POJO.Joueur;
import com.mycompany.projetculdechouette.ServletConnexion;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/ServletCreerCompte")
public class ServletCreerCompte extends HttpServlet {
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
        int age = Integer.parseInt(request.getParameter("age"));
        char sexe = request.getParameter("sexe").charAt(0);
        String ville = request.getParameter("ville");
        
        // Créer un nouvel objet Joueur avec les valeurs saisies
        Joueur nouveauJoueur = new Joueur();
        nouveauJoueur.setPseudo(pseudo);
        nouveauJoueur.setMdp(mdp);
        nouveauJoueur.setAge(age);
        nouveauJoueur.setSexe(sexe);
        nouveauJoueur.setVille(ville);
        
        try {
            daoJoueur.ajouterJoueur(nouveauJoueur);
        } catch (DAOException ex) {
            response.sendError(0, "Erreur de base de données");
        }

        // Rediriger l'utilisateur vers une page de confirmation ou de succès
        response.sendRedirect("PageAccueil.jsp");
    }
}
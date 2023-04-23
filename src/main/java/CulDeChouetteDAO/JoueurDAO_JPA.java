/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Joueur;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Arnaud
 */
public class JoueurDAO_JPA implements IJoueur {
    
    private EntityManagerFactory emf = null;

    @Override
    public void ajouterJoueur(Joueur joueur) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Integer> queryAdresse = em.createQuery("SELECT MAX(j.codeJoueur) FROM Joueur j", Integer.class);
            Integer maxCodeJoueur = queryAdresse.getSingleResult();
            if(maxCodeJoueur != null) {
                joueur.setCodeJoueur(maxCodeJoueur);
            } else {
                joueur.setCodeJoueur(1);
            }
            em.persist(joueur);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la création du joueur " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void modifierJoueur(Joueur joueur) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(joueur);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la mise à jour du joueur " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public boolean supprimerJoueur(Joueur joueur) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(joueur);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la supression du joueur " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Joueur rechercherJoueurParId(int idJoueur) throws DAOException{
        EntityManager em = emf.createEntityManager();
        Joueur joueur = null;
        try {
            em.getTransaction().begin();
            joueur = em.find(Joueur.class, idJoueur);
            em.getTransaction().commit();
        } catch(Exception e) {
            em.getTransaction().rollback();
            throw new DAOException("Erreur lors de la supression du joueur " + e.getMessage());
        } finally {
            em.close();
        }
        return joueur;
    }

    @Override
    public List<Joueur> rechercherTousLesJoueurs() throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<Joueur> query = em.createQuery("SELECT j FROM Joueur j", Joueur.class);
            return query.getResultList();
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la récupération de la liste des Joueurs");
        } finally {
            em.close();
        }
    }

    @Override
    public Joueur connexionJoueur(String pseudo, String motDePasse) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Joueur> query = em.createQuery("SELECT j FROM Joueur j WHERE j.pseudo = :pseudo AND j.mdp = :motDePasse", Joueur.class);
        query.setParameter("pseudo", pseudo);
        query.setParameter("motDePasse", motDePasse);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    public JoueurDAO_JPA() throws DAOException {
        this.emf = ConnexionBDD.getEMF();
    }
    
}

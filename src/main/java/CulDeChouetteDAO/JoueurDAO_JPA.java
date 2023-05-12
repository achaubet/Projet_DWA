/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CulDeChouetteDAO;

import POJO.Joueur;
import POJO.JoueursPartie;
import POJO.Partie;
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
        System.out.println("COUCOU");
        try {
            em.getTransaction().begin();
            TypedQuery<Integer> queryJoueur = em.createQuery("SELECT MAX(j.codeJoueur) FROM Joueur j", Integer.class);
            Integer maxCodeJoueur = queryJoueur.getSingleResult();
            if(maxCodeJoueur != null) {
                maxCodeJoueur++;
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
            em.merge(joueur);
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
            em.remove(em.merge(joueur));
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
        } catch(Exception e) {
            throw new DAOException("Erreur lors de la supression du joueur " + e.getMessage());
        } finally {
            em.close();
        }
        return joueur;
    }
    
    @Override
    public Joueur rechercherJoueurParPseudo(String pseudo) throws DAOException {
            EntityManager em = emf.createEntityManager();
        Joueur joueur = null;
        try {
            joueur =  em.createQuery("SELECT j FROM Joueur j WHERE j.pseudo = :pseudo", Joueur.class)
                    .setParameter("pseudo", pseudo)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DAOException("Erreur lors de de la récupération du joueur: +" + pseudo + ", raison: " + e.getMessage());
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
            em.close();
            return null;
        }
    }
    
    @Override
    public boolean rechercherPseudoExistant(String pseudo) {
        EntityManager em = emf.createEntityManager();
        Joueur joueur = null;
        try {
            joueur =  em.createQuery("SELECT j FROM Joueur j WHERE j.pseudo = :pseudo", Joueur.class)
                    .setParameter("pseudo", pseudo)
                    .getSingleResult();
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
        return joueur!=null;
    }
    
    private List<JoueursPartie> rechercherPartieParIdJoueur(int codeJoueur) throws DAOException {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            TypedQuery<JoueursPartie> query = em.createQuery("SELECT jp FROM JoueursPartie jp WHERE jp.joueursPartiePK.codeJoueur = :codeJoueur", JoueursPartie.class);
            query.setParameter("codeJoueur", codeJoueur);
            return query.getResultList();
        } catch (Exception e) {
            em.close();
            return null;
        }
    }
    
    @Override
    public void updateAllJoueursStats() throws DAOException {
        List<Joueur> joueurs = this.rechercherTousLesJoueurs();
        for(Joueur joueur: joueurs) {
            List<JoueursPartie> joueurParties = this.rechercherPartieParIdJoueur(joueur.getCodeJoueur());
            joueur.setJoueursPartieList(joueurParties);
            int nbParties = joueurParties.size();
            int nbCvTotal = 0;
            int nbSuitesTotal = 0;
            if(nbParties > 0) {
                int nbVictoires = 0;
                int scoreTotal = 0;
                int nbSuitesGagnees = 0;
                int nbCvPerdues = 0;
                for(JoueursPartie joueurPartie: joueurParties) {
                    Partie p = joueurPartie.getPartie();
                    if(joueurPartie.getScore() >= p.getScoreMax()) {
                        nbVictoires++;
                    }
                    nbCvTotal += p.getNbCv();
                    nbSuitesTotal += p.getNbSuites();
                    scoreTotal += joueurPartie.getScore();
                    nbSuitesGagnees += joueurPartie.getSuiteGagnees();
                    nbCvPerdues += joueurPartie.getCvPerdues();
                }
                float scoreMoyen = (float) scoreTotal / nbParties; 
                float moySuitesGagnees = 0;
                float moyCvPerdues = 0;
                if(nbCvTotal > 0) {
                    moyCvPerdues = (float) nbCvPerdues / nbCvTotal;
                }
                if(nbSuitesTotal > 0) {
                    moySuitesGagnees = (float) nbSuitesGagnees / nbSuitesTotal;
                }
                float nbMoyenVictoires = (float) nbVictoires / nbParties;
                joueur.setMoyCvPerdues(moyCvPerdues);
                joueur.setMoySuitesGagnees(moySuitesGagnees);
                joueur.setScoreMoyen(scoreMoyen);
                joueur.setNbVictoires(nbVictoires);
                joueur.setNbMoyenVictoires(nbMoyenVictoires);
                this.modifierJoueur(joueur);
            } else {
                joueur.setMoyCvPerdues(0);
                joueur.setMoySuitesGagnees(0);
                joueur.setScoreMoyen(0);
                joueur.setNbVictoires(0);
                joueur.setNbMoyenVictoires(0);
                this.modifierJoueur(joueur);
            }
        }
    }
    
    public JoueurDAO_JPA() throws DAOException {
        this.emf = ConnexionBDD.getEMF();
    }
   
}

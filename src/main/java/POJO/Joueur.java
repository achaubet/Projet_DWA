/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arnaud
 */
@Entity
@Table(name = "JOUEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByCodeJoueur", query = "SELECT j FROM Joueur j WHERE j.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Joueur.findByPseudo", query = "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo"),
    @NamedQuery(name = "Joueur.findByMdp", query = "SELECT j FROM Joueur j WHERE j.mdp = :mdp"),
    @NamedQuery(name = "Joueur.findByAge", query = "SELECT j FROM Joueur j WHERE j.age = :age"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville"),
    @NamedQuery(name = "Joueur.findByNbVictoires", query = "SELECT j FROM Joueur j WHERE j.nbVictoires = :nbVictoires"),
    @NamedQuery(name = "Joueur.findByNbMoyenVictoires", query = "SELECT j FROM Joueur j WHERE j.nbMoyenVictoires = :nbMoyenVictoires"),
    @NamedQuery(name = "Joueur.findByScoreMoyen", query = "SELECT j FROM Joueur j WHERE j.scoreMoyen = :scoreMoyen"),
    @NamedQuery(name = "Joueur.findByMoySuitesGagnees", query = "SELECT j FROM Joueur j WHERE j.moySuitesGagnees = :moySuitesGagnees"),
    @NamedQuery(name = "Joueur.findByMoyCvPerdues", query = "SELECT j FROM Joueur j WHERE j.moyCvPerdues = :moyCvPerdues")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_JOUEUR")
    private int codeJoueur;
    @Size(max = 32)
    @Column(name = "PSEUDO")
    private String pseudo;
    @Size(max = 16)
    @Column(name = "MDP")
    private String mdp;
    @Column(name = "AGE")
    private int age;
    @Column(name = "SEXE")
    private char sexe;
    @Size(max = 50)
    @Column(name = "VILLE")
    private String ville;
    @Column(name = "NB_VICTOIRES")
    private int nbVictoires;
    @Column(name = "NB_MOYEN_VICTOIRES")
    private float nbMoyenVictoires;
    @Column(name = "SCORE_MOYEN")
    private float scoreMoyen;
    @Column(name = "MOY_SUITES_GAGNEES")
    private float moySuitesGagnees;
    @Column(name = "MOY_CV_PERDUES")
    private float moyCvPerdues;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private List<JoueursPartie> joueursPartieList;

    public Joueur() {
    }

    public Joueur(int codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public int getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(int codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Character getSexe() {
        return sexe;
    }

    public void setSexe(Character sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNbVictoires() {
        return nbVictoires;
    }

    public void setNbVictoires(int nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public float getNbMoyenVictoires() {
        return nbMoyenVictoires;
    }

    public void setNbMoyenVictoires(float nbMoyenVictoires) {
        this.nbMoyenVictoires = nbMoyenVictoires;
    }

    public float getScoreMoyen() {
        return scoreMoyen;
    }

    public void setScoreMoyen(float scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }

    public float getMoySuitesGagnees() {
        return moySuitesGagnees;
    }

    public void setMoySuitesGagnees(float moySuitesGagnees) {
        this.moySuitesGagnees = moySuitesGagnees;
    }

    public float getMoyCvPerdues() {
        return moyCvPerdues;
    }

    public void setMoyCvPerdues(float moyCvPerdues) {
        this.moyCvPerdues = moyCvPerdues;
    }

    @XmlTransient
    public List<JoueursPartie> getJoueursPartieList() {
        return joueursPartieList;
    }

    public void setJoueursPartieList(List<JoueursPartie> joueursPartieList) {
        this.joueursPartieList = joueursPartieList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codeJoueur;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        return this.codeJoueur == other.codeJoueur;
    }

    @Override
    public String toString() {
        return "POJO.Joueur[ codeJoueur=" + codeJoueur + " ]";
    }
    
}

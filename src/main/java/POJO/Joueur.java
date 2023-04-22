/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
    private BigDecimal codeJoueur;
    @Size(max = 32)
    @Column(name = "PSEUDO")
    private String pseudo;
    @Size(max = 16)
    @Column(name = "MDP")
    private String mdp;
    @Column(name = "AGE")
    private BigInteger age;
    @Column(name = "SEXE")
    private Character sexe;
    @Size(max = 50)
    @Column(name = "VILLE")
    private String ville;
    @Column(name = "NB_VICTOIRES")
    private BigInteger nbVictoires;
    @Column(name = "NB_MOYEN_VICTOIRES")
    private BigInteger nbMoyenVictoires;
    @Column(name = "SCORE_MOYEN")
    private BigInteger scoreMoyen;
    @Column(name = "MOY_SUITES_GAGNEES")
    private BigInteger moySuitesGagnees;
    @Column(name = "MOY_CV_PERDUES")
    private BigInteger moyCvPerdues;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private List<JoueursPartie> joueursPartieList;

    public Joueur() {
    }

    public Joueur(BigDecimal codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public BigDecimal getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigDecimal codeJoueur) {
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

    public BigInteger getAge() {
        return age;
    }

    public void setAge(BigInteger age) {
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

    public BigInteger getNbVictoires() {
        return nbVictoires;
    }

    public void setNbVictoires(BigInteger nbVictoires) {
        this.nbVictoires = nbVictoires;
    }

    public BigInteger getNbMoyenVictoires() {
        return nbMoyenVictoires;
    }

    public void setNbMoyenVictoires(BigInteger nbMoyenVictoires) {
        this.nbMoyenVictoires = nbMoyenVictoires;
    }

    public BigInteger getScoreMoyen() {
        return scoreMoyen;
    }

    public void setScoreMoyen(BigInteger scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }

    public BigInteger getMoySuitesGagnees() {
        return moySuitesGagnees;
    }

    public void setMoySuitesGagnees(BigInteger moySuitesGagnees) {
        this.moySuitesGagnees = moySuitesGagnees;
    }

    public BigInteger getMoyCvPerdues() {
        return moyCvPerdues;
    }

    public void setMoyCvPerdues(BigInteger moyCvPerdues) {
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
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "POJO.Joueur[ codeJoueur=" + codeJoueur + " ]";
    }
    
}

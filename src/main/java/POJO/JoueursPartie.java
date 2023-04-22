/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Arnaud
 */
@Entity
@Table(name = "JOUEURS_PARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "JoueursPartie.findAll", query = "SELECT j FROM JoueursPartie j"),
    @NamedQuery(name = "JoueursPartie.findByCodeJoueur", query = "SELECT j FROM JoueursPartie j WHERE j.joueursPartiePK.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "JoueursPartie.findByCodePartie", query = "SELECT j FROM JoueursPartie j WHERE j.joueursPartiePK.codePartie = :codePartie"),
    @NamedQuery(name = "JoueursPartie.findByScore", query = "SELECT j FROM JoueursPartie j WHERE j.score = :score"),
    @NamedQuery(name = "JoueursPartie.findBySuiteGagnees", query = "SELECT j FROM JoueursPartie j WHERE j.suiteGagnees = :suiteGagnees"),
    @NamedQuery(name = "JoueursPartie.findByCvPerdues", query = "SELECT j FROM JoueursPartie j WHERE j.cvPerdues = :cvPerdues")})
public class JoueursPartie implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JoueursPartiePK joueursPartiePK;
    @Column(name = "SCORE")
    private BigInteger score;
    @Column(name = "SUITE_GAGNEES")
    private BigInteger suiteGagnees;
    @Column(name = "CV_PERDUES")
    private BigInteger cvPerdues;
    @JoinColumn(name = "CODE_JOUEUR", referencedColumnName = "CODE_JOUEUR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public JoueursPartie() {
    }

    public JoueursPartie(JoueursPartiePK joueursPartiePK) {
        this.joueursPartiePK = joueursPartiePK;
    }

    public JoueursPartie(BigInteger codeJoueur, BigInteger codePartie) {
        this.joueursPartiePK = new JoueursPartiePK(codeJoueur, codePartie);
    }

    public JoueursPartiePK getJoueursPartiePK() {
        return joueursPartiePK;
    }

    public void setJoueursPartiePK(JoueursPartiePK joueursPartiePK) {
        this.joueursPartiePK = joueursPartiePK;
    }

    public BigInteger getScore() {
        return score;
    }

    public void setScore(BigInteger score) {
        this.score = score;
    }

    public BigInteger getSuiteGagnees() {
        return suiteGagnees;
    }

    public void setSuiteGagnees(BigInteger suiteGagnees) {
        this.suiteGagnees = suiteGagnees;
    }

    public BigInteger getCvPerdues() {
        return cvPerdues;
    }

    public void setCvPerdues(BigInteger cvPerdues) {
        this.cvPerdues = cvPerdues;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (joueursPartiePK != null ? joueursPartiePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JoueursPartie)) {
            return false;
        }
        JoueursPartie other = (JoueursPartie) object;
        if ((this.joueursPartiePK == null && other.joueursPartiePK != null) || (this.joueursPartiePK != null && !this.joueursPartiePK.equals(other.joueursPartiePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "POJO.JoueursPartie[ joueursPartiePK=" + joueursPartiePK + " ]";
    }
    
}

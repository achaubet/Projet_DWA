/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Arnaud
 */
@Embeddable
public class JoueursPartiePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_JOUEUR")
    private int codeJoueur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_PARTIE")
    private int codePartie;

    public JoueursPartiePK() {
    }

    public JoueursPartiePK(int codeJoueur, int codePartie) {
        this.codeJoueur = codeJoueur;
        this.codePartie = codePartie;
    }

    public int getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(int codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public int getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(int codePartie) {
        this.codePartie = codePartie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += codeJoueur;
        hash += codePartie;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JoueursPartiePK)) {
            return false;
        }
        JoueursPartiePK other = (JoueursPartiePK) object;
        if (this.codeJoueur != other.codeJoueur) {
            return false;
        }
        else if (this.codePartie != other.codePartie) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "POJO.JoueursPartiePK[ codeJoueur=" + codeJoueur + ", codePartie=" + codePartie + " ]";
    }
    
}

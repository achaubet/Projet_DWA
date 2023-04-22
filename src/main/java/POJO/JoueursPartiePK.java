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
    private BigInteger codeJoueur;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_PARTIE")
    private BigInteger codePartie;

    public JoueursPartiePK() {
    }

    public JoueursPartiePK(BigInteger codeJoueur, BigInteger codePartie) {
        this.codeJoueur = codeJoueur;
        this.codePartie = codePartie;
    }

    public BigInteger getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(BigInteger codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public BigInteger getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(BigInteger codePartie) {
        this.codePartie = codePartie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JoueursPartiePK)) {
            return false;
        }
        JoueursPartiePK other = (JoueursPartiePK) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "POJO.JoueursPartiePK[ codeJoueur=" + codeJoueur + ", codePartie=" + codePartie + " ]";
    }
    
}

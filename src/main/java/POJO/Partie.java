/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJO;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Arnaud
 */
@Entity
@Table(name = "PARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partie.findAll", query = "SELECT p FROM Partie p"),
    @NamedQuery(name = "Partie.findByCodePartie", query = "SELECT p FROM Partie p WHERE p.codePartie = :codePartie"),
    @NamedQuery(name = "Partie.findByDatePartie", query = "SELECT p FROM Partie p WHERE p.datePartie = :datePartie")})
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODE_PARTIE")
    private int codePartie;
    @Column(name = "DATE_PARTIE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datePartie;
    @Column(name = "SCORE_MAX")
    private int scoreMax;
    @Column(name = "NB_CV")
    private int nbCv;
    @Column(name = "NB_SUITES")
    private int nbSuites;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private List<JoueursPartie> joueursPartieList;

    public Partie() {
    }

    public Partie(int codePartie) {
        this.codePartie = codePartie;
    }

    public int getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(int codePartie) {
        this.codePartie = codePartie;
    }

    public Date getDatePartie() {
        return datePartie;
    }

    public void setDatePartie(Date datePartie) {
        this.datePartie = datePartie;
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
        hash += codePartie;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partie)) {
            return false;
        }
        Partie other = (Partie) object;
        return this.codePartie == other.codePartie;
    }


    @Override
    public String toString() {
        return "POJO.Partie[ codePartie=" + codePartie + " ]";
    }

    public int getScoreMax() {
        return scoreMax;
    }

    public void setScoreMax(int scoreMax) {
        this.scoreMax = scoreMax;
    }

    public int getNbCv() {
        return nbCv;
    }

    public void setNbCv(int nbCv) {
        this.nbCv = nbCv;
    }

    public int getNbSuites() {
        return nbSuites;
    }

    public void setNbSuites(int nbSuites) {
        this.nbSuites = nbSuites;
    }
    
}

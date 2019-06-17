package com.example.GestionMembre.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity représentant le club (Accessible que pour le président).
 * Permet de gérer le début d'exercice et la trésorerie du club de randonnées.
 * 
 * @author Emma/Hugo/Marie
 */
@Entity
public class EnMarche implements Serializable {
    Float tresor;
    Date debutEx;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Constructeur vide. Définit une nouvelle trésorerie avec un solde de 0€.
     */
    public EnMarche() {
        this.tresor = 0F;
        this.debutEx = new Date();
    }
    
    /**
     * Constructeur de la trésorerie de notre club.
     * @param tresor Solde de début d'activité
     */
    public EnMarche(float tresor) {
        this.tresor = tresor;
        this.debutEx = new Date();
    }
    
    /*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    */

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnMarche)) {
            return false;
        }
        EnMarche other = (EnMarche) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Getter sur la trésorerie du club
     * @return Solde de la trésorerie en €
     */
    public Float getTresor() {
        return tresor;
    }

    /**
     * Setter sur la trésorerie du club
     * @param tresor Nouveau solde de la trésorerie en €
     */
    public void setTresor(Float tresor) {
        this.tresor = tresor;
    }

    /**
     * Getter sur la date de début d'exercice du club
     * @return Date du début d'exercice
     */
    public Date getDebutEx() {
        return debutEx;
    }

    /**
     * Setter dur la date de début d'exercice du club
     * @param debutEx Nouvelle date du début d'exercice
     */
    public void setDebutEx(Date debutEx) {
        this.debutEx = debutEx;
    }

    @Override
    public String toString() {
        return "Entities.EnMarche[ id=" + id + " ]";
    }
    
}

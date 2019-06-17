package com.example.GestionMembre.Entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity représentant notre objet Rôle d'un Membre du club
 * 
 * @author Emma/Hugo/Marie
 */
@Entity
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * Énumération des rôles d'un membre.
     * 4 rôles possibles : 
     * Ø Membre,
     * Ø TeamLeader,
     * Ø Secretariat,
     * Ø President.
     * 
     */
    public enum Roles{
        Membre,
        TeamLeader,
        Secretariat,
        President
    };
        
    private Roles titre;

    /**
     * Constructeur vide
     */
    public Role(){}
    
    /**
     * Contructeur d'un rôle d'un membre du club
     * @param titre Titre du role
     * @see Roles
     */
    public Role(Roles titre) {
        this.titre = titre;
    }

    /**
     * Getter sur le titre du rôle d'un membre du club
     * @return Titre du rôle d'un membre du club
     */
    public Roles getTitre() {
        return titre;
    }

    /**
     * Setter sur le titre du rôle d'un membre du club
     * @param titre Nouveau titre du rôle d'un membre du club
     */
    public void setTitre(Roles titre) {
        this.titre = titre;
    }

    /**
     * Getter sur l'identifiant du rôle d'un membre du club
     * @return Identifiant du rôle d'un membre du club
     */
    public Long getId() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Role[ id=" + id + " ]";
    }
    
}

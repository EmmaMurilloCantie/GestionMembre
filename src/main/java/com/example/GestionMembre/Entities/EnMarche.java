/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author emma
 */
@Entity
public class EnMarche implements Serializable {
    Float tresor;
    Date debutEx;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    public EnMarche() {
        this.tresor = 0F;
        this.debutEx = new Date();
    }
    
    public EnMarche(float tresor) {
        this.tresor = tresor;
        this.debutEx = new Date();
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof EnMarche)) {
            return false;
        }
        EnMarche other = (EnMarche) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public Float getTresor() {
        return tresor;
    }

    public void setTresor(Float tresor) {
        this.tresor = tresor;
    }

    public Date getDebutEx() {
        return debutEx;
    }

    public void setDebutEx(Date debutEx) {
        this.debutEx = debutEx;
    }

    @Override
    public String toString() {
        return "Entities.EnMarche[ id=" + id + " ]";
    }
    
}

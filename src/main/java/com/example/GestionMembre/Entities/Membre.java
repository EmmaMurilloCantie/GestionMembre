/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author emma
 */
@Entity
public class Membre implements Serializable {

    class Adress {
        private String ville;
        private String pays;
        
        public Adress() {}

        public Adress(String ville, String pays) {
            this.ville = ville;
            this.pays = pays;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }  
    } 
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    
    @Column(unique= true)
    private String pseudo;
    
    private String mdp;
    
    private Adress adresse;
    
    private Date datecertif;
    private int numLicence;
    private float niveau;
    private Date validPaiement;
    private float montant;
    
    @ManyToMany //(cascade=CascadeType.DETACH)
    private List<Role> role;
    
    
    public Membre(){}
    
    public Membre(String nom, String prenom, String mail, String pseudo, String mdp, String pays, String ville, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.adresse = new Adress(pays, ville);
        this.validPaiement = null;
        this.role = new ArrayList<Role>();
        this.role.add(role);
    }
    
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Adress getAdress() {
        return adresse;
    }

    public void setAdresse(Adress adresse) {
        this.adresse = adresse;
    }
    
    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Date getDatecertif() {
        return datecertif;
    }

    public void setDatecertif(Date datecertif) {
        this.datecertif = datecertif;
    }

    public int getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(int numLicence) {
        this.numLicence = numLicence;
    }

    public float getNiveau() {
        return niveau;
    }

    public void setNiveau(float niveau) {
        this.niveau = niveau;
    }

    public Date getValidPaiement() {
        return validPaiement;
    }

    public void setValidPaiement(Date validPaiement) {
        this.validPaiement = validPaiement;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
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
        if (!(object instanceof Membre)) {
            return false;
        }
        Membre other = (Membre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Membre[ id=" + id + " ]";
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
    
}

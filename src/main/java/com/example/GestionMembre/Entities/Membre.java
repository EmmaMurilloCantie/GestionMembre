package com.example.GestionMembre.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

/**
 * Entity représentant notre objet Membre du club
 * 
 * @author Emma/Hugo/Marie
 */
@Entity
public class Membre implements Serializable {
    
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
    
    //@A VERIFIER
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datecertif;
    
    private int numLicence;
    
    private float niveau;
    
    private Date validPaiement;
    
    private float montant;
    
    private String pays, ville;
    
    @ManyToMany //(cascade=CascadeType.DETACH)
    private List<Role> role;
    
    /**
     * Constructeur vide
     */
    public Membre(){}
    
    /**
     * Constructeur d'un Membre du club
     * 
     * @param nom Nom du membre du club
     * @param prenom Prénom du membre du club
     * @param mail Adresse mail du membre du club
     * @param pseudo Pseudo du membre du club
     * @param mdp Mot de passe du membre du club
     * @param pays Pays du membre du club
     * @param ville Ville du membre du club
     * @param role Rôle du membre du club
     */
    public Membre(String nom, String prenom, String mail, String pseudo, String mdp, String pays, String ville, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.pseudo = pseudo;
        this.mdp = mdp;
        this.pays = pays;
        this.ville = ville;
        this.validPaiement = null;
        this.role = new ArrayList<Role>();
        this.role.add(role);
    }
    
    /**
     * Getter sur le pseudo du membre
     * @return Pseudo du membre
     */
    public String getPseudo() {
        return pseudo;
    }
    
    /**
     * Getter sur le nom du membre
     * @return Nom du membre
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter sur le nom du membre
     * @param nom Nouveau nom du membre
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    
    /**
     * Getter sur le prénom du membre
     * @return Prénom du membre
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Setter sur le prénom du membre
     * @param prenom Nouveau prénom du membre
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter sur l'adresse mail du membre
     * @return Adresse mail du membre
     */
    public String getMail() {
        return mail;
    }

    /**
     * Setter sur l'adresse mail du membre
     * @param mail Nouvelle adresse mail du membre
     */
    public void setMail(String mail) {
        this.mail = mail;
    }

    /**
     * Getter sur le mot de passe du membre
     * @return Mot de passe du membre
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Setter sur le mot de passe du membre
     * @param mdp Nouveau mot de passe du membre
     */
    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    /**
     * Getter sur la date du certificat médical du membre
     * @return Date du certificat médical du membre
     */
    public Date getDatecertif() {
        return datecertif;
    }

    /**
     * Setter sur la date du certificat médical du membre
     * @param datecertif Nouvelle date du certificat médical du membre
     */
    public void setDatecertif(Date datecertif) {
        this.datecertif = datecertif;
    }

    /**
     * Getter sur le numéro de licence du membre
     * @return Numéro de licence du membre
     */
    public int getNumLicence() {
        return numLicence;
    }

    /**
     * Setter sur le numéro de licence du membre
     * @param numLicence Nouveau numéro de licence du membre
     */
    public void setNumLicence(int numLicence) {
        this.numLicence = numLicence;
    }

    /**
     * Getter sur le niveau du membre
     * @return Niveau du membre
     */
    public float getNiveau() {
        return niveau;
    }

    /**
     * Setter sur le niveau du membre
     * @param niveau Nouveau niveau du membre
     */
    public void setNiveau(float niveau) {
        this.niveau = niveau;
    }

    /**
     * Getter sur la date de validation du paiement de la cottisation
     * @return Date de validation du paiement
     */
    public Date getValidPaiement() {
        return validPaiement;
    }

    /**
     * Setter sur la date de validation du paiement de la cottisation
     * @param validPaiement Nouvelle date de validation du paiement
     */
    public void setValidPaiement(Date validPaiement) {
        this.validPaiement = validPaiement;
    }

    /**
     * Getter sur le dernier montant payé pour la cottisation du membre
     * @return Dernier montant payé pour la cottisation du membre
     */
    public float getMontant() {
        return montant;
    }

    /**
     * Setter sur le dernier montant payé pour la cottisation du membre
     * @param montant Nouveau dernier montant payé pour la cottisation du membre
     */
    public void setMontant(float montant) {
        this.montant = montant;
    }

    /**
     * Getter sur le pays du membre
     * @return Pays du membre
     */
    public String getPays() {
        return pays;
    }

    /**
     * Setter sur le pays du membre
     * @param pays Nouveau pays du membre
     */
    public void setPays(String pays) {
        this.pays = pays;
    }

    /**
     * Getter sur la ville du membre
     * @return Ville du membre
     */
    public String getVille() {
        return ville;
    }

    /**
     * Setter sur la ville du membre
     * @param ville Nouvelle ville du membre
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Getter sur l'identifiant du membre
     * @return Identifiant du membre
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter sur l'identifiant du membre
     * @param id Nouvel identifiant du membre
     */
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

    /**
     * Getter de la liste des rôles possédés par le membre.
     * 4 rôles possibles : 
     * Ø Membre,
     * Ø TeamLeader,
     * Ø Secretariat,
     * Ø President.
     * 
     * @return Liste de roles possédés par le membre
     */
    public List<Role> getRole() {
        return role;
    }

    /**
     * Setter de la liste des rôles possédés par le membre.
     * 4 rôles possibles : 
     * Ø Membre,
     * Ø TeamLeader,
     * Ø Secretariat,
     * Ø President.
     * 
     * @param role Nouvelle liste de roles possédés par le membre
     */
    public void setRole(List<Role> role) {
        this.role = role;
    }
    
}

package com.example.GestionMembre.Entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Entity représentant notre objet Adresse d'un membre du club.
 * 
 * @author Emma/Hugo/Marie
 */
@Embeddable
public class Adress implements Serializable{
    private  boolean isNull = true ;
    
    private String ville;
    private String pays;
    
    public static Adress ADRESSE_NULL = new Adress();
        
    /**
     * Constructeur vide
     */
    public Adress() {}

    /**
     * Constructeur d'une adresse d'un membre du club.
     * 
     * @param ville Ville de l'adresse du membre
     * @param pays Pays de l'adresse du membre
     */
    public Adress(String ville, String pays) {
        this.ville = ville;
        this.pays = pays;
    }
    
    /**
     * Getter sur l'adresse du membre 
     * 
     * @param add Adresse sur laquelle on veut effectuer un get
     * @return null si le membre n'a pas d'adresse
     *         l'adresse s'il en possède une
     */
    public static Adress getAdresse(Adress add) {
         if (add == null || add.isNull) {
             return null ;
        }  else {
             return add ;
        }
    }
    
    /**
     * Getter sur la ville de l'adresse 
     * 
     * @return Ville de l'adresse
     */
    public String getVille() {
        return ville;
    }

    /**
     * Setter sur la ville de l'adresse
     * 
     * @param ville Nouvelle ville de l'adresse
     */
    public void setVille(String ville) {
        this.ville = ville;
    }

    /**
     * Getter sur le pays de l'adresse
     *
     * @return Pays de l'adresse
     */
    public String getPays() {
        return pays;
    }

    /**
     * Setter sur le pays de l'adresse
     * 
     * @param pays Nouveau pays de l'adresse
     */
    public void setPays(String pays) {
        this.pays = pays;
    }  
}

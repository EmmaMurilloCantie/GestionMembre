/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Entities;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 *
 * @author emma
 */
@Embeddable
public class Adress implements Serializable{
    private  boolean isNull = true ;
    
    private String ville;
    private String pays;
    
    public static Adress ADRESSE_NULL = new Adress();
        
    public Adress() {}

    public Adress(String ville, String pays) {
        this.ville = ville;
        this.pays = pays;
    }
    
    public  static Adress getAdresse(Adress add) {
         if (add == null || add.isNull) {
             return null ;
        }  else {
             return add ;
        }
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

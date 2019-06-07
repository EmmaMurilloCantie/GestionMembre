/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Service;

import com.example.GestionMembre.DbInit;
import com.example.GestionMembre.Entities.EnMarche;
import com.example.GestionMembre.Entities.Membre;
import com.example.GestionMembre.Entities.Role;
import com.example.GestionMembre.Entities.Role.Roles;
import com.example.GestionMembre.Repositories.RepoEnMarche;
import com.example.GestionMembre.Repositories.RepoMembre;
import com.example.GestionMembre.Repositories.RepoRole;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author emma
 */
@Service
public class GestionMembre {
    @Autowired
    RepoMembre rm;
    @Autowired
    RepoEnMarche rem;
    @Autowired
    RepoRole rr;
    
    /*
    Créer un membre
    @param membre 
    On vérifie que le speudo n'est pas déjà utilisé
    */
    public boolean inscription (Membre membre){
        Iterator membres = rm.findAll().iterator();
        Membre mCourant = new Membre();
        while (membres.hasNext()){
            mCourant = (Membre) membres.next();
            if(mCourant.getPseudo().equals(membre.getPseudo())){
                return false;
            }
        }
        ArrayList<Role> roles = new ArrayList<>();
        Iterator role = rr.findAll().iterator();
        while(role.hasNext()){
            Role rCourant = (Role) role.next();
            if(rCourant.getTitre().equals(Roles.Membre))
                roles.add(rCourant);
        }
        membre.setRole(roles);
        rm.save(membre);
        return true;
        
    }
    
    /*
    Modifier un membre
    @param membre 
    */
    public void modifierM (Membre membre){
        Membre m = (Membre) rm.findById(membre.getId()).get();
        m.setNom(membre.getNom());
        m.setPrenom(membre.getPrenom());
        m.setMail(membre.getMail());
        //m.setPseudo(membre.getPseudo);
        m.setMdp(membre.getMdp());
        m.setAdresse(membre.getVille(), membre.getPays());
        m.setNumLicence(membre.getNumLicence());
        m.setNiveau(membre.getNiveau());
        m.setMontant(membre.getMontant());
        //m.setRole() = new ArrayList<>();
        for(Role r : membre.getRole())
            ajoutRole(membre, r.getTitre());
        rm.save(m);
    }
    
    /*
    Get de la liste des membres
    @return Iterator 
    */
    public Iterator membres (){
        Iterator membres = rm.findAll().iterator();
        return membres;
    }
    
    /*
    Get d'un membre
    @param id d'un Membre
    @return Membre 
    */
    public Membre membre (Long id){
        Membre m = (Membre) rm.findById(id).get();
        return m;
    }
    
    /*
    Connexion à son compte
    @param pseudo
    @param mdp
    Vérifie le mdp et le pseudo
    @return Membre 
    */
    public Membre connexion (String pseudo, String mdp){
        Iterator membres = rm.findAll().iterator();
        Membre mCourant = new Membre();
        while (membres.hasNext()){
            mCourant = (Membre) membres.next();
            if(mCourant.getPseudo().equals(pseudo)){
                break;
            }
        }
        if(mCourant.getPseudo().equals(pseudo) && mCourant.getMdp().equals(mdp)){
            return mCourant;
        }
        return null;
    }
    
    /*
    Valider le paiement
    @param id d'un Membre
    */
    public Date validerPaiement (Long id){
        Membre m = (Membre) rm.findById(id).get();
        Iterator enMarche = rem.findAll().iterator();
        EnMarche app = null;
        while(enMarche.hasNext())
            app = (EnMarche) enMarche.next();
        app.setTresor(app.getTresor()+m.getMontant());
        m.setValidPaiement(new Date());
        rm.save(m);
        rem.save(app);
        return m.getValidPaiement();
    }
    
    public void invaliderPaiement (){
        Iterator enMarche = rem.findAll().iterator();
        EnMarche app = null;
        while(enMarche.hasNext())
            app = (EnMarche) enMarche.next();
        if(app.getDebutEx().getYear() < (new Date()).getYear()){
            Iterator membres = rm.findAll().iterator();
            while (membres.hasNext()){
                Membre mCourant = (Membre) membres.next();
                mCourant.setValidPaiement(null);
                mCourant.setMontant(0F);
                rm.save(mCourant);
            }
        }
        rem.save(app);
    }
    
    
    public Membre ajoutRole (Membre membre, Roles r){
        boolean estPresent = false;
        System.out.println("com.example.GestionMembre.Service.GestionMembre.ajoutRole()"+membre);
        System.out.println("com.example.GestionMembre.Service.GestionMembre.ajoutRole()"+r);
        for(Role rCourant : membre.getRole()){
            if(rCourant.getTitre() == r)
                estPresent = true;
        }
        if(!estPresent){
            ArrayList<Role> roles = new ArrayList<>();
            //roles.add(DbInit.rM);
            Iterator role = rr.findAll().iterator();
            while(role.hasNext()){
                Role rCourant = (Role) role.next();
                if(rCourant.getTitre().equals(r))
                    roles.add(rCourant);
            }
            membre.setRole(roles);
        }
        return membre;
    }
    
    //STATISTIQUES
    public int nbMembres(){
        return (int) rm.count();
    }
    //STATISTIQUES
    public int nbTL(){
        Iterator membres = rm.findAll().iterator();
        int nbTL = 0;
        while(membres.hasNext()){
            Membre mCourant = (Membre) membres.next();
            Iterator roles = mCourant.getRole().iterator();
            while(roles.hasNext()){
                Role rCourant = (Role) roles.next();
                if(rCourant.getId() == 2){
                    nbTL ++;
                }
            } 
        }
        return nbTL;
        
    }
    //STATISTIQUES
    public float montantCottisationsPrevues(){
        Iterator membres = rm.findAll().iterator();
        float montant = 0F;
        
        while(membres.hasNext()){
            Membre mCourant = (Membre) membres.next();
            if(mCourant.getMontant() != 0F && mCourant.getValidPaiement() == null)
                montant += mCourant.getMontant();
        }
        return montant;
        
    }
    //STATISTIQUES
    public float montantCottisationReglees(){
        Iterator membres = rm.findAll().iterator();
        float montant = 0F;
        
        while(membres.hasNext()){
            Membre mCourant = (Membre) membres.next();
            if(mCourant.getMontant() != 0F && mCourant.getValidPaiement() != null)
                montant += mCourant.getMontant();
        }
        return montant;
        
    }
    //RANDO GET TRESO
    public float treso (){
        Iterator enMarches = rem.findAll().iterator();
        EnMarche app = (EnMarche) enMarches.next();
        return app.getTresor();
    }
    //RANDO PATCH TRESO
    public float mtreso (float debit){
        Iterator enMarches = rem.findAll().iterator();
        float montant = 0;
        EnMarche eCourant = (EnMarche) enMarches.next();
        montant = eCourant.getTresor() - debit;
        eCourant.setTresor(montant);
        rem.save(eCourant);
        return montant;
    }
    
    public Date validerCertif (Long id){
        Membre m = (Membre) rm.findById(id).get();
        m.setDatecertif(new Date());
        rm.save(m);
        return m.getDatecertif();
    }
    
    
    /*public void inscription (String nom, String prenom, String mail, String pseudo, String mdp, String adresse){
        Membre m = new Membre(nom, prenom, mail, pseudo, mdp, adresse);
        rm.save(m);
    }*/
    
    /*public void modifier (Long id, String nom, String prenom, String mail, String pseudo, String mdp, String adresse, float montant, Date date, int licence, float expertise){
        Membre m = (Membre) rm.findById(id).get();
        m.setNom(nom);
        m.setPrenom(prenom);
        m.setMail(mail);
        //m.setPseudo(pseudo);
        m.setMdp(mdp);
        m.setAdresse(adresse);
        m.setDatecertif(date);
        m.setNumLicence(licence);
        m.setNiveau(expertise);
        m.setMontant(montant);
        rm.save(m);
    }*/
    
    /* public void validerCertificat (Long id, Date date){
        Membre m = (Membre) rm.findById(id).get();
        m.setDatecertif(date);
        rm.save(m);
    }
    
    public void validerLicence (Long id, int licence){
        Membre m = (Membre) rm.findById(id).get();
        m.setNumLicence(licence);
        rm.save(m);
    }
    
    public void validerExpertise (Long id, float expertise){
        Membre m = (Membre) rm.findById(id).get();
        m.setNiveau(expertise);
        rm.save(m);
    }
    
    public void payer (Long id, float montant){
        Membre m = (Membre) rm.findById(id).get();
        m.setMontant(montant);
        rm.save(m);
    }
    */
    
}

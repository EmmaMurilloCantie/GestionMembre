/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Service;

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
    
    /**
     * Permet à une personne de s'inscrire sur le site et de devenir membre
     * @param membre objet membre 
     * @return false -  si le login est déjà utilisé / le membre n'est pas créé
     *         true  -  login unique / création du nouveau membre
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
    
    /**
     * Permet à un membre de modifier les informations personnels de son profil
     *  Nom; Prénom; Mail; Mdp; Adresse
     * @param membre objet membre à modifier
     */
    public void modifierM (Membre membre){
        Membre m = (Membre) rm.findById(membre.getId()).get();
        m.setNom(membre.getNom());
        m.setPrenom(membre.getPrenom());
        m.setMail(membre.getMail());
        //m.setPseudo(membre.getPseudo);
        m.setMdp(membre.getMdp());
        m.setVille(membre.getVille());
        m.setVille(membre.getPays());
        m.setNumLicence(membre.getNumLicence());
        m.setNiveau(membre.getNiveau());
        m.setMontant(membre.getMontant());
        //m.setRole() = new ArrayList<>();
        for(Role r : membre.getRole()){
            ajoutRole(m, r.getTitre());
        }
        rm.save(m);
    }
    
    /**
     * Renvoie la liste des membres enregistrer dans la base
     * @return Iterator : la liste des membres
     */
    public Iterator membres (){
        Iterator membres = rm.findAll().iterator();
        return membres;
    }
    
    /**
     * Renvoie le membre qui correspond à l'ID en paramètre
     * @param id Long : Id d'un membre
     * @return Objet Membre
     */
    public Membre membre (Long id){
        Membre m = (Membre) rm.findById(id).get();
        return m;
    }
    
    /**
     * Permet à l'utilisateur de se connecter et d'accéder à son compte
     * on vérifie la correspondance du login / mdp
     * @param pseudo : pseudo du membre
     * @param mdp : mdp du membre
     * @return objet Membre qui souhaite se connecter
     *         / ou null si le mdp et/ou login erroné
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
    
    /**
     * Valider le paiement
     * @param id : id d'un membre 
     * @return Date de validation de paiement
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
        for(Role rCourant : membre.getRole()){
            if(rCourant.getTitre() == r){
                estPresent = true;
            }
        }
        if(!estPresent){
            List<Role> roles = membre.getRole();
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
    /**
     * Compte le nombre de personnes inscrites
     * @return int le nombre de personnes
     */
    public int nbMembres(){
        return (int) rm.count();
    }
    
    //STATISTIQUES
    /**
     * Compte le nombre de Team Leader 
     * @return int le nombre de Team Leader
     */
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
    /**
     * Calcule le montant des cottisations prévues
     * @return float le montant des cottisations prévues
     */
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
    /**
     * Calcule le montant des cottisations reglees
     * @return float le montent des cottisations reglees
     */
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
    /***
     * Récupère le montant de la trésorerie
     * @return float trésorerie 
     */
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
    
}

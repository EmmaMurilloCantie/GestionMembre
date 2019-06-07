/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Controller;

import com.example.GestionMembre.Entities.Membre;
import com.example.GestionMembre.Service.GestionMembre;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author emma
 */
@RestController
@RequestMapping("/membre")
public class RestControler {
    @Autowired
    GestionMembre gm;
    
    //MEMBRE
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Membre membre (@PathVariable Long id){
        return gm.membre(id);
    }
    
     //MEMBRE
    @RequestMapping(value="/", method = RequestMethod.GET)
    public Iterator membres (){
        return gm.membres();
    }
    
    //MEMBRE
    @RequestMapping(value="/subscribe", method = RequestMethod.POST)
    public void subscribe (@RequestBody Membre membreAcreer){
        gm.inscription(membreAcreer);
        System.out.println("com.example.GestionMembre.Controller.RestControler.subscribe()"+membreAcreer);
    }
    
    //MEMBRE
    @RequestMapping(value="/login", method = RequestMethod.POST)
    public Membre login (@RequestBody Membre membreAconnecter){
       return gm.connexion(membreAconnecter.getPseudo(), membreAconnecter.getMdp());
    }
    
    //MEMBRE
    @RequestMapping(value="/modifier", method = RequestMethod.PATCH)
    public void modifier (@RequestBody Membre membreMaJ){
        gm.modifierM(membreMaJ);
    }
    
    //SECRETAIRE
    @RequestMapping(value="/validation/{id}", method = RequestMethod.GET)
    public Date validerPaiement (@PathVariable Long id){
        return gm.validerPaiement(id);
    }
    
    //SECRETAIRE
    @RequestMapping(value = "/certificat/{id}", method = RequestMethod.GET)
    public Date validerCertif(@PathVariable Long id) {
        return gm.validerCertif(id);
    }
    
    //PRESIDENT @TOASK
    @RequestMapping(value="/statistiques", method = RequestMethod.GET)
    public HashMap statistique (){
        HashMap<String, Float> stat = new HashMap<>();
        stat.put("nbMembre", (float) gm.nbMembres());
        stat.put("nbTeamLeader", (float) gm.nbTL());
        //TL en fonction de leur niveau
        stat.put("MtCottisationsPrevues", gm.montantCottisationsPrevues());
        stat.put("MtCottisationsReglees", gm.montantCottisationReglees());
        return stat;
    }
    
    //RANDO
    @RequestMapping(value="/treso", method = RequestMethod.GET)
    public float treso (){
        return gm.treso();
    }
    
    //RANDO
    @RequestMapping(value="/treso", method = RequestMethod.PATCH)
    public float mTreso (@PathVariable float debit){
        return gm.mtreso(debit);
    }
    
    //RANDO
    @RequestMapping(value="/niveau", method = RequestMethod.GET)
    public float niveau (@PathVariable Long id){
        return gm.membre(id).getNiveau();
    }
    
    //RANDO
    @RequestMapping(value="/apte", method = RequestMethod.GET)
    public boolean apte (@PathVariable Long id){
        Date date = gm.membre(id).getDatecertif();
        if (date == null){
            return false;
        }else{
            return true;
        }
    }
    
    /*//RANDO
    @RequestMapping(value="/niveau", method = RequestMethod.GET)
    public Date regle (@PathVariable Long id){
        return gm.membre(id).getValidPaiement();
    }*/
    
    // donner la treso ok
    // modifier la tréso débiter / montant à débiter ok
    // le niveau d'un membre / id  ok
    // l'apte ou pas apte (certif) / id ok
    // en regle (payer) / id 
    // envoie des stats / encours budgetaire / total du coup des randos (CF/CV)
    
    
    //@Todo
    //GestionRando
    // Get des coûts pour maj de la tréso 
    // Est ce qu'on prévoit la modification des membres ?
    // IBAN en base ?
    // est ce que quelqu'un plus fort peut s'incrire ?
    
    
    
    /*//MEMBRE
    @RequestMapping(value="/subscribe", method = RequestMethod.POST)
    public void subscribe (@RequestBody Membre membreAcreer ){
        gm.inscription(membreAcreer.getNom(), membreAcreer.getPrenom(), membreAcreer.getMail(), membreAcreer.getPseudo(), membreAcreer.getMdp(), membreAcreer.getAdresse());
    }*/
    
    /*//MEMBRE
    @RequestMapping(value="/payer/{id}", method = RequestMethod.PATCH)
    public void payer (@PathVariable Long id, 
            @RequestBody Membre membreMaJ){
        gm.payer(id, montant);
    }*/
    
    
   /*//SECRETAIRE
    @RequestMapping(value="/valider/payer/{id}", method = RequestMethod.GET)
    public void validerPaiement (@PathVariable Long id){
        gm.validerPaiement(id);
    }
    
    //SECRETAIRE
    @RequestMapping(value="/valider/certificat/{id}", method = RequestMethod.PATCH)
    public void certificat (@PathVariable Long id,
            @RequestBody Date date){
        gm.validerCertificat(id, date);
    }
    
    //SECRETAIRE
    @RequestMapping(value="/valider/licence/{id}", method = RequestMethod.PATCH)
    public void licence (@PathVariable Long id,
            @RequestBody int licence){
        gm.validerLicence(id, licence);
    }
    
    //SECRETAIRE
    @RequestMapping(value="/valider/expertise/{id}", method = RequestMethod.PATCH)
    public void expertise (@PathVariable Long id,
            @RequestBody float niveau){
        gm.validerExpertise(id, niveau);
    }*/
    
}

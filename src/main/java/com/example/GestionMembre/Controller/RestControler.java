package com.example.GestionMembre.Controller;

import com.example.GestionMembre.Entities.Membre;
import com.example.GestionMembre.Entities.Role;
import com.example.GestionMembre.Entities.Role.Roles;
import com.example.GestionMembre.Service.GestionMembre;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Rest Controller pour la gestion des membres du club sur une application WEB.
 * @author Emma/Hugo/Marie
 */
@RestController
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
    public float enCoursBudgetaire(){
        return gm.enCoursBudgetaire();
    }
    
    //RANDO
    @RequestMapping(value="/treso/debit", method = RequestMethod.PUT)
    public float mTreso (@RequestBody float debit){
        return gm.mtreso(debit);
    }
    
    //RANDO
    @RequestMapping(value="/niveau/{id}", method = RequestMethod.GET)
    public float niveau (@PathVariable Long id){
        return gm.membre(id).getNiveau();
    }
    
    //RANDO
    @RequestMapping(value="/apte/{id}", method = RequestMethod.GET)
    public boolean apte (@PathVariable Long id){
        Date date = gm.membre(id).getDatecertif();
        System.out.println("com.example.GestionMembre.Controller.RestControler.apte()" + date);
        if (date == null){
            return false;
        }else{
            return true;
        }
    }
    
    //RANDO
    @RequestMapping(value="/role/{id}/{aRole}", method = RequestMethod.GET)
    public boolean role (@PathVariable Long id, @PathVariable String aRole){
        List<Role> roles = gm.membre(id).getRole();
        for (Role role : roles){
            if (role.getTitre().equals(Roles.valueOf(aRole))){
                return true;
            }        
        }
        return false;
    }
    
}

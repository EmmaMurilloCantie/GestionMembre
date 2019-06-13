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
 * Classe permettant de gérer les membres du club comme décrit ci-dessous. La
 * gestion des membres a pour but : Ø De laisser un membre s'inscrire sur la
 * plateforme WEB Ø De laisser un membre s'acquitter de sa cottisation annuelle,
 * le secrétaire valide le paiement Ø De laisser un membre fournir un certificat
 * médical attestant qu'il est apte Ø De laisser le secrétaire gérer le niveau
 * des membres Ø Le système de gestion des membres gère automatiquement la durée
 * de validité des certificats médicaux et la durée de validité des paiements Ø
 * De laisser le président avoir accès aux statistiques propres aux membres Ø La
 * trésorerie du club est géré dans Gestion Membre
 *
 * @author Emma/Hugo/Marie
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
     * Création d'un membre au sein de l'application Va Marcher. Le futur membre
     * a la possibilité de s'inscrire tout seul via l'interface WEB
     *
     * @param membre Membre fournit par le front
     * @return false - si le login est déjà utilisé / le membre n'est pas créé
     * true - login unique / création du nouveau membre
     */
    public boolean inscription(Membre membre) {
        Iterator membres = rm.findAll().iterator();
        Membre mCourant = new Membre();
        while (membres.hasNext()) {
            mCourant = (Membre) membres.next();
            if (mCourant.getPseudo().equals(membre.getPseudo())) {
                return false;
            }
        }
        ArrayList<Role> roles = new ArrayList<>();
        Iterator role = rr.findAll().iterator();
        while (role.hasNext()) {
            Role rCourant = (Role) role.next();
            if (rCourant.getTitre().equals(Roles.Membre)) {
                roles.add(rCourant);
            }
        }
        membre.setRole(roles);
        rm.save(membre);
        return true;
    }

    /**
     * Méthode permettant à un membre de modifier les informations personnelles
     * de son profil. Les données modifiables : Nom, Prénom, Mail, Mdp, Adresse
     *
     * @param membre Membre à modifier
     */
    public void modifierM(Membre membre) {
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
        for (Role r : membre.getRole()) {
            ajoutRole(m, r.getTitre());
        }
        rm.save(m);
    }

    /**
     * Renvoie la liste des membres enregistrés dans la base
     *
     * @return une liste des membres sous forme d'itérateur
     */
    public Iterator membres() {
        Iterator membres = rm.findAll().iterator();
        return membres;
    }

    /**
     * Renvoie le membre qui correspond à l'ID en paramètre
     *
     * @param id Identifiant d'un membre
     * @return Membre
     */
    public Membre membre(Long id) {
        Membre m = (Membre) rm.findById(id).get();
        return m;
    }

    /**
     * Permet à l'utilisateur de se connecter et d'accéder à son compte on
     * vérifie la correspondance du login / mdp
     *
     * @param pseudo Pseudo du membre
     * @param mdp Mdp du membre
     * @return Membre qui souhaite se connecter / ou null si le mdp et/ou login
     * erroné
     */
    public Membre connexion(String pseudo, String mdp) {
        Iterator membres = rm.findAll().iterator();
        Membre mCourant = new Membre();
        while (membres.hasNext()) {
            mCourant = (Membre) membres.next();
            if (mCourant.getPseudo().equals(pseudo)) {
                break;
            }
        }
        if (mCourant.getPseudo().equals(pseudo) && mCourant.getMdp().equals(mdp)) {
            return mCourant;
        }
        return null;
    }

    /**
     * Méthode permettant de valider le paiement
     *
     * @param id Identifiant d'un membre
     * @return Date de validation de paiement
     */
    public Date validerPaiement(Long id) {
        Membre m = (Membre) rm.findById(id).get();
        Iterator enMarche = rem.findAll().iterator();
        EnMarche app = null;
        while (enMarche.hasNext()) {
            app = (EnMarche) enMarche.next();
        }
        app.setTresor(app.getTresor() + m.getMontant());
        m.setValidPaiement(new Date());
        rm.save(m);
        rem.save(app);
        return m.getValidPaiement();
    }

    /**
     * Méthode permettant d'invalider le paiement et les certificats médicaux
     * de tous les membres de Gestion Membre. Cette méthode nous permet de gérer 
     * de manière autonome la durée de validité des paiements
     */
    public void invaliderPaiementCertif() {
        Iterator enMarche = rem.findAll().iterator();
        EnMarche app = null;
        while (enMarche.hasNext()) {
            app = (EnMarche) enMarche.next();
        }
        if (app.getDebutEx().getYear() < (new Date()).getYear()) {
            Iterator membres = rm.findAll().iterator();
            while (membres.hasNext()) {
                Membre mCourant = (Membre) membres.next();
                mCourant.setValidPaiement(null);
                mCourant.setMontant(0F);
                mCourant.setDatecertif(null);
                app.setDebutEx(new Date());
                rm.save(mCourant);
            }
        }
        rem.save(app);
    }

    /**
     * Méthode permettant d'ajouter un rôle à un membre. Un membre peut avoir
     * plusieurs rôles parmis ceux ci-après : Ø Membre, Ø TeamLeader, Ø
     * Secretariat, Ø President
     *
     * Ces rôles ont une notion d'ordonnancement : Membre &lt TeamLeader &lt
     * Secrétariat &lt Président
     *
     * @param membre Membre à qui ajouter le rôle
     * @param r Rôle à ajouter
     * @return Nouveau membre
     * @see Role
     */
    public Membre ajoutRole(Membre membre, Roles r) {
        boolean estPresent = false;
        for (Role rCourant : membre.getRole()) {
            if (rCourant.getTitre() == r) {
                estPresent = true;
            }
        }
        if (!estPresent) {
            List<Role> roles = membre.getRole();
            //roles.add(DbInit.rM);
            Iterator role = rr.findAll().iterator();
            while (role.hasNext()) {
                Role rCourant = (Role) role.next();
                if (rCourant.getTitre().equals(r)) {
                    roles.add(rCourant);
                }
            }
            membre.setRole(roles);
        }
        return membre;
    }

    //STATISTIQUES
    /**
     * Méthode permettant de retourner le nombre de personnes inscrites,
     * c'est-à-dire le nombre de membres du club.
     *
     * @return Le nombre de membres du club
     */
    public int nbMembres() {
        return (int) rm.count();
    }

    //STATISTIQUES
    /**
     * Méthode permettant de retourner le nombre de Team Leaders
     *
     * @return Le nombre de Team Leader
     */
    public int nbTL() {
        Iterator membres = rm.findAll().iterator();
        int nbTL = 0;
        while (membres.hasNext()) {
            Membre mCourant = (Membre) membres.next();
            Iterator roles = mCourant.getRole().iterator();
            while (roles.hasNext()) {
                Role rCourant = (Role) roles.next();
                if (rCourant.getId() == 2) {
                    nbTL++;
                }
            }
        }
        return nbTL;

    }

    //STATISTIQUES
    /**
     * Méthode permettant de calculer le montant des cottisations prévues
     *
     * @return Le montant des cottisations prévues
     */
    public float montantCottisationsPrevues() {
        Iterator membres = rm.findAll().iterator();
        float montant = 0F;

        while (membres.hasNext()) {
            Membre mCourant = (Membre) membres.next();
            if (mCourant.getMontant() != 0F && mCourant.getValidPaiement() == null) {
                montant += mCourant.getMontant();
            }
        }
        return montant;
    }

    //STATISTIQUES
    /**
     * Méthode permettant de calculer le montant des cottisations reglées
     *
     * @return Le montant des cottisations reglées
     */
    public float montantCottisationReglees() {
        Iterator membres = rm.findAll().iterator();
        float montant = 0F;

        while (membres.hasNext()) {
            Membre mCourant = (Membre) membres.next();
            if (mCourant.getMontant() != 0F && mCourant.getValidPaiement() != null) {
                montant += mCourant.getMontant();
            }
        }
        return montant;
    }

    //STATISTIQUES
    /**
     * En cours budgétaire du club de randonnée
     *
     * @return En cours budgétaire du club
     */
    public float enCoursBudgetaire() {
        Iterator enMarches = rem.findAll().iterator();
        EnMarche app = (EnMarche) enMarches.next();
        return app.getTresor();
    }

    //RANDO PATCH TRESO
    /**
     * Méthode permettant de modifier (débiter) le solde de la trésorerie du
     * club.
     *
     * @param debit Montant à débiter au solde de la trésorerie du club
     * @return Nouveau solde de la trésorerie
     */
    public float mtreso(float debit) {
        Iterator enMarches = rem.findAll().iterator();
        float montant = 0;
        EnMarche eCourant = (EnMarche) enMarches.next();
        montant = eCourant.getTresor() - debit;
        eCourant.setTresor(montant);
        rem.save(eCourant);
        return montant;
    }

    /**
     * Méthode permettant au secrétaire de valider le certificat médical d'un
     * membre. Cette action n'est possible que par le Secrétaire : vérifié dans
     * le front
     *
     * @param id Identifiant du membre dont il faut valider le certificat
     * médical
     * @return La date de validation du certificat médical
     */
    public Date validerCertif(Long id) {
        Membre m = (Membre) rm.findById(id).get();
        m.setDatecertif(new Date());
        rm.save(m);
        return m.getDatecertif();
    }
}

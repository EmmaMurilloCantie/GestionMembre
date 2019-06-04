/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre;

import com.example.GestionMembre.Entities.EnMarche;
import com.example.GestionMembre.Entities.Membre;
import com.example.GestionMembre.Entities.Role;
import com.example.GestionMembre.Repositories.RepoEnMarche;
import com.example.GestionMembre.Repositories.RepoMembre;
import com.example.GestionMembre.Repositories.RepoRole;
import java.util.ArrayList;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author emma
 */
@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInit implements CommandLineRunner {
    private RepoRole rr;
    private RepoMembre rm;
    private RepoEnMarche rem;

    public DbInit(RepoRole rr, RepoMembre rm, RepoEnMarche rem){
        this.rr = rr;
        this.rm = rm;
        this.rem = rem;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.rr.deleteAll();
        this.rm.deleteAll();
        this.rem.deleteAll();

        Role rP = new Role("President");
        Role rTL = new Role("TeamLeader");
        Role rS = new Role("Secrétaire");
        Role rM = new Role("Membre");
        Membre m1 = new Membre("pasero","hugo","mail@mail","huto96","huto96","","add",rM);
        Membre m2 = new Membre("murillo-cantie","emma","mail2@mail2","mcEmma","mcEmma","","nenuphars",rS);
        Membre m3 = new Membre("roca","marie","mail3@mail3","rocaca","rocaca","","la vache",rP);

        EnMarche em = new EnMarche(1000);

        //ArrayList<Role> roles = new ArrayList<>();
        //roles.add(rM);
        //m1.setRole(roles);
        this.rr.save(rP);
        this.rr.save(rTL);
        this.rr.save(rS);
        this.rr.save(rM);
        this.rm.save(m1);
        this.rm.save(m2);
        this.rm.save(m3);
        this.rem.save(em);
        System.out.println(" -- Database has been initialized");
    }
}
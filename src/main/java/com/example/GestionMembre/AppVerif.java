package com.example.GestionMembre;

import com.example.GestionMembre.Repositories.RepoEnMarche;
import com.example.GestionMembre.Repositories.RepoMembre;
import com.example.GestionMembre.Repositories.RepoRole;
import com.example.GestionMembre.Service.GestionMembre;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 *
 * @author Emma/Hugo/Marie
 */
@Component
@ConditionalOnProperty(name = "app.app-verif", havingValue = "true")
public class AppVerif implements CommandLineRunner  {
    private RepoRole rr;
    private RepoMembre rm;
    private RepoEnMarche rem;
    private GestionMembre gm;

    public AppVerif(GestionMembre gm){
        this.gm = gm;
    }

    @Override
    public void run(String... args) throws Exception {
        this.gm.invaliderPaiementCertif();
    }
    
}

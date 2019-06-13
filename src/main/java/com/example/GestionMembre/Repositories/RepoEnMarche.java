package com.example.GestionMembre.Repositories;

import com.example.GestionMembre.Entities.EnMarche;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour la gestion de la trésorerie du club sur une application WEB
 * @author Emma/Hugo/Marie
 */
public interface RepoEnMarche extends CrudRepository<EnMarche, Long> {
    
}

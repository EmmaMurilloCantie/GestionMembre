package com.example.GestionMembre.Repositories;

import com.example.GestionMembre.Entities.Membre;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour la gestion des membres du club sur une application WEB
 * @author Emma/Hugo/Marie
 */
public interface RepoMembre extends CrudRepository<Membre, Long> {
    
}

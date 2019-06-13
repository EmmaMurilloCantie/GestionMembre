package com.example.GestionMembre.Repositories;

import com.example.GestionMembre.Entities.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour la gestion des rôles que peuvent prendre les membres du club sur une application WEB
 * @author Emma/Hugo/Marie
 */
public interface RepoRole extends CrudRepository<Role, Long> {
    
}

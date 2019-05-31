/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre.Repositories;

import com.example.GestionMembre.Entities.Role;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author emma
 */
public interface RepoRole extends CrudRepository<Role, Long> {
    
}

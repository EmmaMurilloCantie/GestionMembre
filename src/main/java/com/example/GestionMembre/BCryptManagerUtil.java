/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.GestionMembre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author emma
 */
@Component
public class BCryptManagerUtil {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BCryptManagerUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Bean(name = "passwordEncoder")
    public static PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.dao;

import com.lp2.SmartParking.modelo.Usuario;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author Valentina
 */
public interface UsuarioDAO extends CrudRepository<Usuario, Integer> {
     @Override
    public List<Usuario> findAll(); 
    
    public Usuario findByRut (String rut);
    public Usuario findById (int id);
    
}

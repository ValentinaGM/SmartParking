/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.dao;

import com.lp2.SmartParking.modelo.UsuarioBase;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Valentina
 */
public interface UsuarioBaseDAO extends CrudRepository<UsuarioBase, Integer> {
    public UsuarioBase findByRut (String rut);
}

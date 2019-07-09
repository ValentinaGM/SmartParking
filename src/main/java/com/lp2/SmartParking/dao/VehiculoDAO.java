/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.dao;

import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.Vehiculo;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Valentina
 */
public interface VehiculoDAO extends CrudRepository<Vehiculo, Integer> {
    public Vehiculo findByPatente(String p);
    
    public Vehiculo findById(int id);
    
    public Vehiculo findByPatenteAndUsuarioid(String p, Usuario usuarioId);
    
    @Override
    public List<Vehiculo> findAll();
    
    public List<Vehiculo> findByUsuarioid(Usuario usuarioId);
}
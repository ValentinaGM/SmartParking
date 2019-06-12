/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.modelo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Size;

/**
 *
 * @author Valentina
 */
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity 
public abstract class UsuarioBase {
     
    @Column(name="id",nullable = false, length = 11)
    @Id
    @GeneratedValue (strategy = GenerationType.TABLE)
    private Long id;
    
    @Basic(optional = false)
    @Size(min = 1, max = 12)
    @Column(name = "rut")
    private String rut;
    
    @Size(max = 50)
    @Column(name = "contrase\u00f1a")
    private String contraseña;

    public UsuarioBase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
}

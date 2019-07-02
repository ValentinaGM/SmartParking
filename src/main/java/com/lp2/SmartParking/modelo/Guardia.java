/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Valentina
 */
@Entity
@Table(name = "guardia")
public class Guardia extends UsuarioBase implements Serializable {

    
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "Estacionamientoid", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Estacionamiento estacionamientoid;

    private static final long serialVersionUID = 1L;
    
    @Column(name = "telefono")
    private Integer telefono;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "guardiaid", fetch = FetchType.LAZY)
    private List<Turnos> turnosList; 

    public Guardia() {
    }


    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }


    public List<Turnos> getTurnosList() {
        return turnosList;
    }

    public void setTurnosList(List<Turnos> turnosList) {
        this.turnosList = turnosList;
    }

    @Override
    public String toString() {
        return "com.lp2.SmartParking.modelo.Guardia[ id=" + this.getId() + " ]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Estacionamiento getEstacionamientoid() {
        return estacionamientoid;
    }

    public void setEstacionamientoid(Estacionamiento estacionamientoid) {
        this.estacionamientoid = estacionamientoid;
    }

}
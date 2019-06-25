/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.GuardiaDAO;
import com.lp2.SmartParking.dao.PuestoDAO;
import com.lp2.SmartParking.modelo.Puesto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Valentina
 */
@Controller
public class GuardiaController {

    @Autowired
    private GuardiaDAO gDAO;
    @Autowired
    private PuestoDAO pDAO;

  //este metodo lleva a la vistaguardia, funciona de la misma forma que el de vistausuario
    @GetMapping("/vistaGuardia")
    public String page(Model model) {
        //se obtienen los puestos
        List<Puesto> puestos = pDAO.findAll();
        //esto solo imprime en la consola (no es necesario, sólo para probar que se estaban devolviendo de la bd)
        System.out.println(puestos.get(0));
        //se envian al model de la vista
        model.addAttribute("puestosBD", puestos); 
        return "vistaGuardia";
    }
    //este hay que borrarlo, no se esta usando JIJIJ
    @PostMapping("/cambiarEstado")
    public String cambiarEstado(Model model) {

        return "vistaGuardia";
    }
    //este metodo cambia el valor del estado de un puesto, se llama cada vez que se clickea un checkbox y se le pasa el id del checkbox que corresponde al puesto
    @PostMapping("asignarpuesto/{id}")
    @ResponseBody
    public void asignar(@PathVariable("id") int idPuesto) { 
        //primero busca el puesto por el id
        Puesto p = pDAO.findById(idPuesto);
        //si el estado es true(ocupado) se cambia a false (desocupado) y viceversa
        if (p.isEstado()) {
            p.setEstado(false);
        } else { 
            p.setEstado(true);
        }
        //guarda el puesto con el estado cambiado
        pDAO.save(p);     
    }
}
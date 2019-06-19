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

    @GetMapping("/vistaGuardia")
    public String page(Model model) {
        List<Puesto> puestos = pDAO.findAll();
        System.out.println(puestos.get(0));
        model.addAttribute("puestosBD", puestos); 
        return "vistaGuardia";
    }

    @PostMapping("/cambiarEstado")
    public String cambiarEstado(Model model) {

        return "vistaGuardia";
    }

    @PostMapping("asignarpuesto/{id}")
    @ResponseBody
    public void asignar(@PathVariable("id") int idPuesto) {        
        Puesto p = pDAO.findById(idPuesto); 
        if (p.isEstado()) {
            p.setEstado(false);
        } else { 
            p.setEstado(true);
        }
        
        pDAO.save(p);     
    }

}

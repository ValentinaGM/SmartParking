/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.GuardiaDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Valentina
 */
@Controller
public class GuardiaController {
    private GuardiaDAO gDAO;
    
    
    @GetMapping("/vistaGuardia")
    public String page(Model model) {
       
        return "vistaGuardia";
    }
    
    @PostMapping("/cambiarEstado")
    public String cambiarEstado(Model model) {
        
        return "vistaGuardia";
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.GuardiaDAO;
import com.lp2.SmartParking.modelo.Guardia;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Valentina
 */
@Controller
public class GuardiaController {
    
     @Autowired
    private GuardiaDAO gDAO;
    
    @GetMapping("/")
    public String guardias(Model model) {
        
        List<Guardia> guardias = (List<Guardia>) gDAO.findAll();
        model.addAttribute("guardia", guardias);
        return "login";
    }
    @PostMapping("/inscribeUsuario")
    public String inscribirForm(@ModelAttribute Guardia guardia){
        System.out.println(guardia.getNombre()); 
        gDAO.save(guardia);
        return "login";
   
    }
    
}

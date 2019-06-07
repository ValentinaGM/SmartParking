/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.modelo.Usuario;
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
public class UsuarioController {
    @Autowired
    private UsuarioDAO uDAO;
    
    @GetMapping("/inscripcion")
    public String inscripcion(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "inscripcion";
    }
    @PostMapping("/inscribeEquipo")
    public String inscribirForm(@ModelAttribute Usuario usuario){
        System.out.println(usuario.getId()); 
        uDAO.save(usuario);
        return "index";
        
    }
}

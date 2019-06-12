/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.modelo.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


/**
 *
 * @author Valentina
 */

@Controller
public class UsuarioController {
    
    @Autowired
    private UsuarioDAO uDAO;
    
    @GetMapping("/login")
    public String usuario(Model model) {
        
       // List<Usuario> usuarios = uDAO.findAll();
        model.addAttribute("Usuario", new Usuario());
        
        return "login"; 
    }
    @PostMapping("/login")
    public String loginForm(@ModelAttribute Usuario usuario, Model model){
        String r = usuario.getRut();
        Usuario ubd = uDAO.findByRut(r);
       
        model.addAttribute("valido", ubd.getContraseña().equals(usuario.getContraseña()));
           
        return "login";
    }
} 


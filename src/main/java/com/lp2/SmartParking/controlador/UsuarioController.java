/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioBaseDAO;
import com.lp2.SmartParking.modelo.Guardia;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.UsuarioBase;
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
    private UsuarioBaseDAO uDAO;

    @GetMapping("/login")
    public String usuario(Model model) {

        // List<Usuario> usuarios = uDAO.findAll();
        //  model.addAttribute("Usuario", new Usuario());
        //preguntaar
        return "login";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute UsuarioBase usuario, Model model) {
        model.addAttribute("invalido", false);
        String r = usuario.getRut();
        String p = usuario.getContraseña();
        UsuarioBase ubd = uDAO.findByRut(r);
        String vista = "";
        if (ubd != null) {
            if (ubd instanceof Usuario) {
                vista = "vistaUsuario";
            } else if (ubd instanceof Guardia) {
                vista = "vistaGuardia";
            }
        } else {
            model.addAttribute("invalido", true);
            vista = "login";
        }
        return vista;
    }

    @GetMapping("/registrar")
    public String usuarios(Model model) {

        model.addAttribute("Usuario", new Usuario());
        return "registrar";
    }

    @PostMapping("/registrar")
    public String inscribirForm(@ModelAttribute Usuario usuario) {
        System.out.println(usuario.getNombre());
        uDAO.save(usuario);
        return "registrar";
    }

    @GetMapping("/vistaUsuario")
    public String page(Model model) {

        return "vistaUsuario";
    }

}

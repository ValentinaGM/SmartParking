package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.modelo.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class LoginController {

    @Autowired
    UsuarioDAO uDAO;

    /*
    @RequestMapping(value = "/registrarr", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "usuario") Usuario usuario, Model model) {
        String rut = usuario.getRut();
        String pass = usuario.getContraseña();

        return "index";
        }
     */
}

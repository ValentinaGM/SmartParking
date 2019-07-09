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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class LoginController {

    @Autowired
    private UsuarioBaseDAO ubDAO;

    @GetMapping("/login")
    public String usuario(Model model, HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);

        if (sesion != null) {
            Object usuario = sesion.getAttribute("usuarioLogueado");

            if (usuario instanceof Usuario) {
                return "redirect:/vistaUsuario";

            } else if (usuario instanceof Guardia) {
                return "redirect:/vistaGuardia";
            }
        }

        model.addAttribute("usuario", new Usuario());

        return "login";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute UsuarioBase usuario, Model model, HttpServletRequest request) {

        model.addAttribute("invalido", false);
        String r = usuario.getRut();
        String p = usuario.getContraseña();

        UsuarioBase ubd = ubDAO.findByRut(r);

        if (ubd != null && ubd.getContraseña().equals(p)) {
            HttpSession sesion = request.getSession();

            sesion.setAttribute("usuarioLogueado", ubd);
            if (ubd instanceof Usuario) {

                return "redirect:/vistaUsuario";

            } else if (ubd instanceof Guardia) {

                return "redirect:/vistaGuardia";
            }

        } else {

            model.addAttribute("invalido", true);
            model.addAttribute("usuario", new Usuario());

            return "login";

        }

        return null;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/index";
    }

}

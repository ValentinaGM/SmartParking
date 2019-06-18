/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.PuestoDAO;
import com.lp2.SmartParking.dao.UsuarioBaseDAO;
import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.modelo.Guardia;
import com.lp2.SmartParking.modelo.Puesto;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.UsuarioBase;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
    private UsuarioBaseDAO ubDAO;
    @Autowired
    private UsuarioDAO uDAO;
    @Autowired
    private PuestoDAO pDAO;

    @GetMapping("/login")
    public String usuario(Model model) {

        // List<Usuario> usuarios = uDAO.findAll();
        //  model.addAttribute("Usuario", new Usuario());
        //preguntaar
        return "login";
    }

    @PostMapping("/login")
    public void loginForm(@ModelAttribute UsuarioBase usuario, Model model, HttpServletResponse response) throws IOException {
        model.addAttribute("invalido", false);
        String r = usuario.getRut();
        String p = usuario.getContraseña();
        UsuarioBase ubd = ubDAO.findByRut(r);
        String vista = "";
        if (ubd != null) {
            if (ubd instanceof Usuario) {
                response.sendRedirect("vistaUsuario");
            } else if (ubd instanceof Guardia) {
                response.sendRedirect("vistaGuardia");
            }
        } else {
            model.addAttribute("invalido", true);
            response.sendRedirect("login");
        }
     
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
        List<Puesto> puestos = pDAO.findAll();
        System.out.println(puestos.get(0));
        model.addAttribute("puestosBD", puestos);
        return "vistaUsuario";
    }

}

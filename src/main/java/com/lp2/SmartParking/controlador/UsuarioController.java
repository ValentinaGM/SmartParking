/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.EstacionamientoDAO;
import com.lp2.SmartParking.dao.PuestoDAO;
import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.dao.VehiculoDAO;
import com.lp2.SmartParking.modelo.Guardia;
import com.lp2.SmartParking.modelo.Puesto;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.Vehiculo;
import java.util.List;
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
public class UsuarioController {

    @Autowired
    private UsuarioDAO uDAO;
    @Autowired
    private PuestoDAO pDAO;
    @Autowired
    private EstacionamientoDAO eDAO;
    @Autowired
    private VehiculoDAO vDAO;

    @GetMapping("/registrar")
    public String usuarios(Model model) {

        model.addAttribute("usuario", new Usuario());
        return "registrar";
    }

    @PostMapping("/registrar")
    public String inscribirForm(@ModelAttribute Usuario usuario) {

        usuario.setEstacionamientoid(eDAO.findById(1));
        uDAO.save(usuario);

        return "redirect:/registrar";
    }

    @GetMapping("/vistaUsuario")
    public String page(Model model, HttpServletRequest request) {
        HttpSession sesion = request.getSession(false);

        if (sesion != null) {
            Object usuario = sesion.getAttribute("usuarioLogueado");

            if (usuario instanceof Usuario) {
                model.addAttribute("usuario", usuario);
                Usuario usu = (Usuario) usuario;
                List<Vehiculo> vehiculos = vDAO.findByUsuarioid(usu);
                List<Puesto> puestos = pDAO.findAll();
                model.addAttribute("puestosBD", puestos);
                model.addAttribute("vehiculosBD", vehiculos);
                return "vistaUsuario";
            } else if (usuario instanceof Guardia) {
                return "redirect:/vistaGuardia";
            }
        }
        return "redirect:/login";
    }
}

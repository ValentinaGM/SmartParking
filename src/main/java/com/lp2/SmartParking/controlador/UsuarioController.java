/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.PuestoDAO;
import com.lp2.SmartParking.dao.UsuarioBaseDAO;
import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.dao.VehiculoDAO;
import com.lp2.SmartParking.modelo.Guardia;
import com.lp2.SmartParking.modelo.Puesto;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.UsuarioBase;
import com.lp2.SmartParking.modelo.Vehiculo;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @Autowired
    private VehiculoDAO vDAO;

    @GetMapping("/login")
    public String usuario(Model model) {

        // List<Usuario> usuarios = uDAO.findAll();
        //  model.addAttribute("Usuario", new Usuario());
        //aun no se si sirve
        return "login";
    }

    @PostMapping("/login")
    public String loginForm(@ModelAttribute UsuarioBase usuario, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        model.addAttribute("invalido", false);
        String r = usuario.getRut();
        String p = usuario.getContraseña();
        UsuarioBase ubd = ubDAO.findByRut(r);

        if (ubd != null && ubd.getContraseña().equals(p)) {
            HttpSession sesion = request.getSession();
            if (ubd instanceof Usuario) {
                response.sendRedirect("vistaUsuario");
                sesion.setAttribute("usuario", ubd);
            } else if (ubd instanceof Guardia) {
                response.sendRedirect("vistaGuardia");
                sesion.setAttribute("usuario", ubd);
            }
        } else {
            model.addAttribute("error", true);
            return "login";

        }
        return null;
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

@PostMapping("/registrarVehiculo")      
    public void vehiculoForm(@RequestBody Vehiculo vehiculo, HttpServletResponse response) throws IOException {         
        vDAO.save(vehiculo);        
        
    }
    

    @GetMapping("/vistaUsuario")
    public String page(Model model) {
        List<Puesto> puestos = pDAO.findAll();
        model.addAttribute("puestosBD", puestos);
        return "vistaUsuario";
    }
    @ModelAttribute("usuario")
    public UsuarioBase getUsuario(HttpServletRequest request) {
        // Obtener la sesion
        HttpSession sesion = request.getSession(false);
       
        // Si hay sesion
        if (sesion != null) {
            // Obtener objeto de usuario
            Object objeto = sesion.getAttribute("usuario");
 
            // Si el objeto es de tipo UsuarioBase
            if (objeto instanceof UsuarioBase) {
                return (UsuarioBase) objeto;
            }
        }
 
        // No hay objeto, retornar null
        return null;
    }

}

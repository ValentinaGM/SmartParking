/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.EstacionamientoDAO;
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
    private UsuarioDAO uDAO;
    @Autowired
    private PuestoDAO pDAO;
    @Autowired
    private EstacionamientoDAO eDAO;

    //este metodo dirige a la vista registrar
    @GetMapping("/registrar")
    public String usuarios(Model model) {
        
       model.addAttribute("usuario", new Usuario());
        return "registrar";
    }
    //este metodo lo usa el formulario de registro de usuarios
    @PostMapping("/registrar")
    public String inscribirForm(@ModelAttribute Usuario usuario) {
        System.out.println(usuario.getNombre());
        usuario.setEstacionamientoid(eDAO.findById(1));                
        //con el ModelAttribute se obtienen los datos del formulario y se guardan en la bd con el save
        uDAO.save(usuario);
        //vuelve a la vista registrar
        return "redirect:/registrar";
    }
 
    //este metodo lleva a la vistausuario
    @GetMapping("/vistaUsuario")     
    public String page(Model model, HttpServletRequest request) {
        
        // Obtener la sesion
        HttpSession sesion = request.getSession(false);
       
        // Si hay sesion
        if (sesion != null) {
            // Obtener objeto de usuario
            Object usuario = sesion.getAttribute("usuarioLogueado");
            // Si el objeto es de tipo UsuarioBase
            if (usuario instanceof Usuario) {
                model.addAttribute("usuario", usuario);
                //se obtiene la lista de todos los puestos de estacionamiento de la bd
                List<Puesto> puestos = pDAO.findAll();      
                //se agrega la lista al model para poder ser leido en la vista con el "th:each" de la tabla
                model.addAttribute("puestosBD", puestos);                
                return "vistaUsuario";
            } else if (usuario instanceof Guardia) {
                return "redirect:/vistaGuardia";
            }
        } 
        // No hay objeto, retornar login
        return "redirect:/login";        
    }
}
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

    //este metodo dirige a la vista registrar
    @GetMapping("/registrar")
    public String usuarios(Model model) {
        
       model.addAttribute("usuario", new Usuario());
        return "registrar";
    }
    //este metodo lo usa el formulario de registro de usuarios
    @PostMapping("/registrar")
    public String inscribirForm(@ModelAttribute Usuario usuario) {
        //se le asigna como estacionamientoid el de la ufro que es el único creado
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
            // Si el objeto es de tipo Usuario
            if (usuario instanceof Usuario) {
                model.addAttribute("usuario", usuario);
                //se tranforma el Object a tipo Usuario para usarlo en el metodo siguiente
                Usuario usu = (Usuario) usuario;
                //se obtiene la lista de todos los vehiculos que tiene registrado el usuario logueado
                List<Vehiculo> vehiculos = vDAO.findByUsuarioid(usu);
                //se obtiene la lista de todos los puestos creados en la bd
                List<Puesto> puestos = pDAO.findAll();      
                //se agregan las listas al model para ser usadas por la vista
                //los puestos se usan para crear los espacios de estacionamiento
                //los vehiculos se usan en el modal de eliminar vehiculo, para que muestre las patentes de los vehiculos del usuario
                model.addAttribute("puestosBD", puestos);
                model.addAttribute("vehiculosBD", vehiculos);
                return "vistaUsuario";
            // si es de tipo guardia, ir a la vista guardia (esto para evitar que un guardia pueda ir a la vista usuario)
            } else if (usuario instanceof Guardia) {
                return "redirect:/vistaGuardia";
            }
        } 
        // No hay sesion, retornar login
        return "redirect:/login";        
    }
}
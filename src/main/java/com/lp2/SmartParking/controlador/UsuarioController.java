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

  //este metodo crea el objeto usuario en todas las vistas y lo agrega al model
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
    
    //este metodo nos lleva a la vista del login
    @GetMapping("/login")
    public String usuario(Model model) {

        // List<Usuario> usuarios = uDAO.findAll();
        //  model.addAttribute("Usuario", new Usuario());
        //aun no se si sirve
        return "login";
    }
    //este metodo es llamado por el formulario del login
    @PostMapping("/login")
    public String loginForm(@ModelAttribute UsuarioBase usuario, Model model, HttpServletResponse response, HttpServletRequest request) throws IOException {
        //agrega el atributo invalido en falso (se empieza considerando que el usuario y clave coinciden)
        model.addAttribute("invalido", false);
        //se obtiene el rut y contraseña del formulario
        String r = usuario.getRut();
        String p = usuario.getContraseña();
        //se busca el usuario por rut en la bd, devuelve un usuario normal o guardia
        UsuarioBase ubd = ubDAO.findByRut(r);       
       //si el usuario existe (distinto de null) y la contraseña coincide con la del usuario
        if (ubd != null && ubd.getContraseña().equals(p)) {
        //se obtiene el objeto sesion (permite guardar el usuario en todas las vistas)
        HttpSession sesion = request.getSession();
            //si el usuario de la bd es de tipo usuario
            if (ubd instanceof Usuario) {
                //se agrega el usuario al sesion
                sesion.setAttribute("usuario", ubd );
                //retorna a la vista usuario
                response.sendRedirect("vistaUsuario");
            //si el usuario de la bd es de tipo guardia
            } else if (ubd instanceof Guardia) {
                //se agrega el usuario al sesion
                sesion.setAttribute("usuario", ubd );
                //retorna a la vista guardia
                response.sendRedirect("vistaGuardia");
                
            }
        //si el usuario no existe o la contraseña no coincide
        } else {
            //se cambia el valor del atributo invalido a true, para mostrar el mensaje de error en la vista
            model.addAttribute("invalido", true);
            //se devuelve al login
            return "login";
            
        }
        //se devuelve nulo para que no tire error el metodo, pero esto nunca pasará
            return null;
    }
    //este metodo dirige a la vista registrar
    @GetMapping("/registrar")
    public String usuarios(Model model) {
        //esto no tiene funcionalidad, se puede comentar jajaja
        model.addAttribute("Usuario", new Usuario());
        return "registrar";
    }
    //este metodo lo usa el formulario de registro de usuarios
    @PostMapping("/registrar")
    public String inscribirForm(@ModelAttribute Usuario usuario) {
        System.out.println(usuario.getNombre());
        //con el ModelAttribute se obtienen los datos del formulario y se guardan en la bd con el save
        uDAO.save(usuario);
        //vuelve a la vista registrar
        return "registrar";
    }
    //este metodo se utiliza al registrar un vehiculo (aun no funciona bien)
    @PostMapping("/registrarVehiculo")      
    public void vehiculoForm(@RequestBody Vehiculo vehiculo, HttpServletResponse response) throws IOException {  
        //el RequestBody obtiene los datos que vienen del axios en el javascript de la vistausuario, y se guardan en la bd con el save
        vDAO.save(vehiculo);        
        
    }
    //este metodo lleva a la vistausuario
    @GetMapping("/vistaUsuario")     
    public String page(Model model) {
        //se obtiene la lista de todos los puestos de estacionamiento de la bd
        List<Puesto> puestos = pDAO.findAll();      
        //se agrega la lista al model para poder ser leido en la vista con el "th:each" de la tabla
        model.addAttribute("puestosBD", puestos);   
        return "vistaUsuario";
    }
}
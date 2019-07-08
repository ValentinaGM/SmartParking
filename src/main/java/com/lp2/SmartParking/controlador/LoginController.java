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
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Valentina
 */
@Controller
public class LoginController {
    @Autowired
   private UsuarioBaseDAO ubDAO;
  //este metodo nos lleva a la vista del login
    @GetMapping("/login")
    public String usuario(Model model) {

        // List<Usuario> usuarios = uDAO.findAll();
        model.addAttribute("usuario", new Usuario());
        
        return "login";
    }
    //este metodo es llamado por el formulario del login
    @PostMapping("/login")
    public String loginForm(@ModelAttribute UsuarioBase usuario, Model model, HttpServletRequest request) {
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
            sesion.setAttribute("usuarioLogueado", ubd );
            if (ubd instanceof Usuario) {
                //retorna a la vista usuario
                return "redirect:/vistaUsuario";
            //si el usuario de la bd es de tipo guardia
            } else if (ubd instanceof Guardia) {              
                //retorna a la vista guardia
                return "redirect:/vistaGuardia";                
            }
        //si el usuario no existe o la contraseña no coincide
        } else {
            //se cambia el valor del atributo invalido a true, para mostrar el mensaje de error en la vista
            model.addAttribute("invalido", true);
            //se devuelve al login
            return "redirect:/login";
            
        }
        //se devuelve nulo para que no tire error el metodo, pero esto nunca pasará
            return null;
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        
        request.getSession().invalidate();
        
        return "redirect:/index";
    }

    }
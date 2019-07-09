/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.EstacionamientoDAO;
import com.lp2.SmartParking.dao.GuardiaDAO;
import com.lp2.SmartParking.dao.PuestoDAO;
import com.lp2.SmartParking.dao.TicketDAO;
import com.lp2.SmartParking.dao.VehiculoDAO;
import com.lp2.SmartParking.modelo.Guardia;
import com.lp2.SmartParking.modelo.Puesto;
import com.lp2.SmartParking.modelo.Ticket;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.Vehiculo;
import java.time.Duration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Valentina
 */
@Controller
public class GuardiaController {

    @Autowired
    private GuardiaDAO gDAO;
    @Autowired
    private PuestoDAO pDAO;
    @Autowired
    private VehiculoDAO vDAO;
    @Autowired
    private EstacionamientoDAO eDAO;
     @Autowired
    private TicketDAO tDAO;

 //este metodo lleva a la vistaguardia, funciona de la misma forma que el de vistausuario
    @GetMapping("/vistaGuardia")
    public String page(Model model, HttpServletRequest request) {
        
        // Obtener la sesion
        HttpSession sesion = request.getSession(false);
       
        // Si hay sesion
        if (sesion != null) {
            // Obtener objeto de usuario
            Object usuario = sesion.getAttribute("usuarioLogueado");
            // Si el objeto es de tipo UsuarioBase
            if (usuario instanceof Guardia) {
                model.addAttribute("usuario", usuario);
                //se obtiene la lista de todos los puestos de estacionamiento de la bd
                List<Puesto> puestos = pDAO.findAll();   
                List<Vehiculo> vehiculos = vDAO.findAll();   
                //se agrega la lista al model para poder ser leido en la vista con el "th:each" de la tabla
                model.addAttribute("puestosBD", puestos); 
                model.addAttribute("vehiculosBD", vehiculos); 
                return "vistaGuardia";
            } else if (usuario instanceof Usuario) {
                return "redirect:/vistaUsuario";
            }
        } 
        // No hay objeto, retornar login
        return "redirect:/login"; 
    }
    
    //este metodo cambia el valor del estado de un puesto, se llama cada vez que se clickea un checkbox y se le pasa el id del checkbox que corresponde al puesto
    @PostMapping("asignarpuesto/{id}/{patente}")
    @ResponseBody
    public void asignar(@RequestBody Ticket t, @PathVariable("id") int idPuesto, @PathVariable("patente") String patente) { 
        //primero busca el puesto por el id
        Puesto p = pDAO.findById(idPuesto);
        Vehiculo v = vDAO.findByPatente(patente);
        t.setEstacionamientoid(eDAO.findById(1)); 
        t.setVehiculoid(v);        
        //si el estado es true(ocupado) se cambia a false (desocupado) y viceversa        
        p.setEstado(true);        
        p.setVehiculoid(v);
        //guarda el puesto con el estado cambiado
        pDAO.save(p);    
        tDAO.save(t);
    }
    
    @PostMapping("desasignarpuesto/{id}")
    @ResponseBody
    public void desasignar(@RequestBody Ticket tfin, @PathVariable("id") int idPuesto) { 
        //primero busca el puesto por el id
        Puesto p = pDAO.findById(idPuesto);      
        
        Ticket t = tDAO.findByVehiculoid(p.getVehiculoid());    
        
        long tiempoEstacionado = Duration.between(t.getInicio(), tfin.getFin()).toMinutes();
        t.setFin(tfin.getFin());
        t.setTiempoEstacionado(tiempoEstacionado);
        t.setEstacionamientoid(eDAO.findById(1));
        p.setEstacionamientoid(eDAO.findById(1));
        t.setVehiculoid(null);
        p.setVehiculoid(null);
        //si el estado es true(ocupado) se cambia a false (desocupado) y viceversa       
        p.setEstado(false);        
        //guarda el puesto con el estado cambiado
        pDAO.save(p);    
        tDAO.save(t);
    }
}
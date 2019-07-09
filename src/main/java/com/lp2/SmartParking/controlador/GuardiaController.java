/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.EstacionamientoDAO;
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
            // Si el objeto es de tipo Guardia
            if (usuario instanceof Guardia) {
                model.addAttribute("usuario", usuario);
                //se obtiene la lista de todos los puestos de estacionamiento de la bd
                List<Puesto> puestos = pDAO.findAll();  
                //se obtiene la lista de todos los vehiculos registrados en la bd
                List<Vehiculo> vehiculos = vDAO.findAll();   
                //se agrega las listas al model de la vista
                //los puestos son para crear cada espacio de estacionamiento
                //los vehiculos se usan al momento de asignar un espacio a ocupado, para mostrar las patentes de los vehiculos registrados
                model.addAttribute("puestosBD", puestos); 
                model.addAttribute("vehiculosBD", vehiculos); 
                return "vistaGuardia";
            // Si el objeto es de tipo Usuario ir a vista usuario
            } else if (usuario instanceof Usuario) {
                return "redirect:/vistaUsuario";
            }
        } 
        // No hay objeto, retornar login
        return "redirect:/login"; 
    }
    
    //este metodo se usa por el boton del modal cuando se quiere cambiar un puesto a ocupado
    @PostMapping("asignarpuesto/{id}/{patente}")
    //es ResponseBody porque no redirige a ninguna vista
    @ResponseBody
    //se le entrega los datos del ticket (en este caso sólo con la hora de entrada), id puesto y la patente. Esto a traves del axios en el javascript
    public void asignar(@RequestBody Ticket t, @PathVariable("id") int idPuesto, @PathVariable("patente") String patente) { 
        //se busca el puesto por el id
        Puesto p = pDAO.findById(idPuesto);
        //se busca el vehiculo por la patente
        Vehiculo v = vDAO.findByPatente(patente);
        //se les asigna el estacionamiento de la UFRO al ticket, que es el único creado
        t.setEstacionamientoid(eDAO.findById(1)); 
        //se le asigna el vehiculo al ticket
        t.setVehiculoid(v);        
        //se cambia el estado del puesto a true (ocupado)       
        p.setEstado(true);   
        //se le asigna el vehiculo al puesto
        p.setVehiculoid(v);
        //guarda el puesto con el estado cambiado
        pDAO.save(p);   
        //guarda el ticket creado
        tDAO.save(t);
    }
    //este metodo se usa por el boton del modal cuando se quiere desocupar un puesto
    @PostMapping("desasignarpuesto/{id}")
    @ResponseBody
    //se le entrega los datos del ticket (en este caso la hora de salida) y el id puesto. Esto a traves del axios en el javascript
    public void desasignar(@RequestBody Ticket tfin, @PathVariable("id") int idPuesto) { 
        //primero busca el puesto por el id
        Puesto p = pDAO.findById(idPuesto);      
        //se busca el ticket (con el id del vehiculo) que contiene la hora de inicio 
        Ticket t = tDAO.findByVehiculoid(p.getVehiculoid());    
        //se calcula el tiempo que estuvo estacionado
        //para esto se usa la clase Duration que da la duracion entre 2 tiempos en minutos
        long tiempoEstacionado = Duration.between(t.getInicio(), tfin.getFin()).toMinutes();
        //se le asigna la hora de salida o fin al ticket
        t.setFin(tfin.getFin());
        //se asigna el tiempo estacionado al ticket
        t.setTiempoEstacionado(tiempoEstacionado);
        //se asigna el estacionamiento de la UFRO al puesto y al ticket
        t.setEstacionamientoid(eDAO.findById(1));
        p.setEstacionamientoid(eDAO.findById(1));
        //se borra el vehiculo del ticket y del puesto, ya que se fue
        t.setVehiculoid(null);
        p.setVehiculoid(null);
        //se cambia el estado del puesto a false(desocupado)
        p.setEstado(false);        
        //guarda el puesto con el estado cambiado
        pDAO.save(p);    
        //guarda el ticket con los datos
        tDAO.save(t);
    }
}
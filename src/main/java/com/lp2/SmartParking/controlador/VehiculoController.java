/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.dao.VehiculoDAO;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Valentina
 */
@Controller
public class VehiculoController {
    @Autowired
    private VehiculoDAO vDAO;
    @Autowired
    private UsuarioDAO uDAO;
    
     //este metodo se utiliza al registrar un vehiculo 
    @PostMapping("registrarVehiculo/{id}") 
    @ResponseBody
    //se le entrega los datos del vehiculo y el id del usuario logueado, esto a traves del axios en el javascript
    public void vehiculoForm(@RequestBody Vehiculo vehiculo, @PathVariable(name="id") int idUsuario) {
        //se busca el usuario
        Usuario usu = uDAO.findById(idUsuario); 
        //se le asigna el usuario al vehiculo
        vehiculo.setUsuarioid(usu);
        //se guarda el vehiculo
        vDAO.save(vehiculo);        
    }
    //este metodo se utiliza al eliminar un vehiculo 
    @PostMapping("eliminarVehiculo/{id}/{patente}") 
    @ResponseBody
    //se le entrega la patente del vehiculo y el id del usuario logueado
    public void eliminarVehiculo(@PathVariable(name="id") int idUsuario, @PathVariable(name="patente") String patente) {
        //se busca el usuario
        Usuario usu = uDAO.findById(idUsuario);
        //se busca el vehiculo por usuario y por patente
        Vehiculo v = vDAO.findByPatenteAndUsuarioid(patente, usu);
        //se eliminar el vehiculo
        vDAO.delete(v);
    }
    
}
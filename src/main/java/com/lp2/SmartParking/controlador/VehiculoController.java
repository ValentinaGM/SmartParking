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

    @PostMapping("registrarVehiculo/{id}")
    @ResponseBody
    public void vehiculoForm(@RequestBody Vehiculo vehiculo, @PathVariable(name = "id") int idUsuario) {
        Usuario usu = uDAO.findById(idUsuario);
        vehiculo.setUsuarioid(usu);
        vDAO.save(vehiculo);
    }

    @PostMapping("eliminarVehiculo/{id}/{patente}")
    @ResponseBody
    public void eliminarVehiculo(@PathVariable(name = "id") int idUsuario, @PathVariable(name = "patente") String patente) {
        Usuario usu = uDAO.findById(idUsuario);
        Vehiculo v = vDAO.findByPatenteAndUsuarioid(patente, usu);
        vDAO.delete(v);
    }

}

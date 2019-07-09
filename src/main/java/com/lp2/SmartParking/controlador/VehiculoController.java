/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import com.lp2.SmartParking.dao.VehiculoDAO;
import com.lp2.SmartParking.modelo.Usuario;
import com.lp2.SmartParking.modelo.UsuarioBase;
import com.lp2.SmartParking.modelo.Vehiculo;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
     //este metodo se utiliza al registrar un vehiculo (aun no funciona bien)
    @PostMapping("registrarVehiculo/{id}") 
    @ResponseBody
    public void vehiculoForm(@RequestBody Vehiculo vehiculo, @PathVariable(name="id") int idUsuario, HttpServletRequest request) {
        
        Usuario usu = uDAO.findById(idUsuario);
        System.out.println(usu.getId());
        vehiculo.setUsuarioid(usu);

        //el RequestBody obtiene los datos que vienen del axios en el javascript de la vistausuario, y se guardan en la bd con el save
        vDAO.save(vehiculo);        
    } 
    
}

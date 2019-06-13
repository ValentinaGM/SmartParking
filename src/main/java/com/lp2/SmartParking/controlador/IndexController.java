
package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    UsuarioDAO uDAO;
    
    @RequestMapping("/")
    public String index(){
        
    return "index";
    }
    
    @RequestMapping("/nosotros")
    public String nosotros(){
    return "nosotros";
    }
}

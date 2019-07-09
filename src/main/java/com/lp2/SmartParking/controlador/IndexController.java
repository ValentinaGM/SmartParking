/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Valentina
 */
@Controller
public class IndexController {
    
    @GetMapping("/index")
    public String page(Model model) {
        
        return "index";
    }
    
    @GetMapping("/nosotros")
    public String nosotros(Model model) {
        
        return "nosotros";
    }
    
}

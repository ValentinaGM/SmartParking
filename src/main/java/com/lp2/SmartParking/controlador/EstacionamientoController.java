package com.lp2.SmartParking.controlador;

import com.lp2.SmartParking.dao.EstacionamientoDAO;
import com.lp2.SmartParking.modelo.Estacionamiento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author eduardo
 */
@RequestMapping("/estacionamiento")
@Controller
public class EstacionamientoController {

    @Autowired
    EstacionamientoDAO eDAO;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lista", eDAO.findAll());
        return "estacionamiento/listar";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lp2.SmartParking.dao;

import com.lp2.SmartParking.modelo.Ticket;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Valentina
 */
public interface TicketDAO extends CrudRepository<Ticket, Integer> {
    
}

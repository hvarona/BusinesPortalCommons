/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.generic;

import com.portal.business.commons.models.Address;
import java.util.List;
/**
 *
 * @author frank
 */
public abstract class AbstractAddress {
     
    public abstract boolean  deleteAddress(Address address);
    public abstract boolean updateAddress(Address address);
    public abstract List<Address> listAddress(Address address);
    public abstract Address findtAddress(Address address);
    public abstract boolean insertAddress(Address address); 
    
}

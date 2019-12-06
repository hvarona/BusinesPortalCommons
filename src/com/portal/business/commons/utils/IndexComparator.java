/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.portal.business.commons.utils;

import com.portal.business.commons.models.ReportParameter;
import java.util.Comparator;

/**
 *
 * @author yalmea
 */
public class IndexComparator implements Comparator<Object>{


    public int compare(Object emp1, Object emp2) {
        Integer index1 = ((ReportParameter) emp1).getIndexOrder();
        Integer index2 = ((ReportParameter) emp2).getIndexOrder();
        return index1.compareTo(index2);
    }
    
    
}

package com.portal.business.commons.utils;

import java.io.Serializable;

public class SalesRecordWSMediaType implements Serializable {

    public static final String APPLICATION = "application";
    public static final String APPLICATION_FORM_URLENCODED = "x-www-form-urlencoded";
    private String type;
    private String subtype;

    public SalesRecordWSMediaType() {
        this.type = APPLICATION;
        this.subtype = APPLICATION_FORM_URLENCODED;
    }

    public SalesRecordWSMediaType(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }
}

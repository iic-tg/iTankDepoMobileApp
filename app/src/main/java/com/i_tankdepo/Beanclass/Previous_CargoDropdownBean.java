package com.i_tankdepo.Beanclass;

import java.io.Serializable;

/**
 * Created by Metaplore on 12/1/2016.
 */

public class Previous_CargoDropdownBean implements Serializable {

    String id;
    String code;
    String desc;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

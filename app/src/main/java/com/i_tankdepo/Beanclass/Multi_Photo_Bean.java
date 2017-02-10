package com.i_tankdepo.Beanclass;

import java.io.Serializable;

/**
 * Created by Sriram on 2/7/2017.
 */

public class Multi_Photo_Bean implements Serializable {

    String base64;



    String name;
    String length;

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }
}

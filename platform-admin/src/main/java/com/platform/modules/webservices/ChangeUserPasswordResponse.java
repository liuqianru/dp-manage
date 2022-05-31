
package com.platform.modules.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "out"
})
@XmlRootElement(name = "changeUserPasswordResponse")
public class ChangeUserPasswordResponse {

    protected boolean out;

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean value) {
        this.out = value;
    }

}

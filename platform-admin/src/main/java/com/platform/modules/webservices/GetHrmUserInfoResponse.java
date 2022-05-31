
package com.platform.modules.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "out"
})
@XmlRootElement(name = "getHrmUserInfoResponse")
public class GetHrmUserInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfUserBean out;

    public ArrayOfUserBean getOut() {
        return out;
    }

    public void setOut(ArrayOfUserBean value) {
        this.out = value;
    }

}

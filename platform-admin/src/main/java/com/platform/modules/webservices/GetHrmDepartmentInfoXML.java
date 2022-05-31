
package com.platform.modules.webservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "in0",
    "in1"
})
@XmlRootElement(name = "getHrmDepartmentInfoXML")
public class GetHrmDepartmentInfoXML {

    @XmlElement(required = true, nillable = true)
    protected String in0;
    @XmlElement(required = true, nillable = true)
    protected String in1;

    public String getIn0() {
        return in0;
    }

    public void setIn0(String value) {
        this.in0 = value;
    }

    public String getIn1() {
        return in1;
    }

    public void setIn1(String value) {
        this.in1 = value;
    }

}


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
@XmlRootElement(name = "getHrmDepartmentInfoResponse")
public class GetHrmDepartmentInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfDepartmentBean out;

    public ArrayOfDepartmentBean getOut() {
        return out;
    }

    public void setOut(ArrayOfDepartmentBean value) {
        this.out = value;
    }

}

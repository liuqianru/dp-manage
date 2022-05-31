
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
@XmlRootElement(name = "getHrmJobTitleInfoResponse")
public class GetHrmJobTitleInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfJobTitleBean out;

    public ArrayOfJobTitleBean getOut() {
        return out;
    }

    public void setOut(ArrayOfJobTitleBean value) {
        this.out = value;
    }

}


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
@XmlRootElement(name = "getHrmSubcompanyInfoResponse")
public class GetHrmSubcompanyInfoResponse {

    @XmlElement(required = true, nillable = true)
    protected ArrayOfSubCompanyBean out;

    public ArrayOfSubCompanyBean getOut() {
        return out;
    }

    public void setOut(ArrayOfSubCompanyBean value) {
        this.out = value;
    }

}

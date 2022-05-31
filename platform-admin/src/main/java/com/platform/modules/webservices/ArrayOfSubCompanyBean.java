
package com.platform.modules.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfSubCompanyBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "subCompanyBean"
})
public class ArrayOfSubCompanyBean {

    @XmlElement(name = "SubCompanyBean", nillable = true)
    protected List<SubCompanyBean> subCompanyBean;

    public List<SubCompanyBean> getSubCompanyBean() {
        if (subCompanyBean == null) {
            subCompanyBean = new ArrayList<SubCompanyBean>();
        }
        return this.subCompanyBean;
    }

}

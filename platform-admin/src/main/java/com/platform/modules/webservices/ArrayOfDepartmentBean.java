
package com.platform.modules.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDepartmentBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "departmentBean"
})
public class ArrayOfDepartmentBean {

    @XmlElement(name = "DepartmentBean", nillable = true)
    protected List<DepartmentBean> departmentBean;

    public List<DepartmentBean> getDepartmentBean() {
        if (departmentBean == null) {
            departmentBean = new ArrayList<DepartmentBean>();
        }
        return this.departmentBean;
    }

}

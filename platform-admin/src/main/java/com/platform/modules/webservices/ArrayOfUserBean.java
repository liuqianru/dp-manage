
package com.platform.modules.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfUserBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "userBean"
})
public class ArrayOfUserBean {

    @XmlElement(name = "UserBean", nillable = true)
    protected List<UserBean> userBean;

    public List<UserBean> getUserBean() {
        if (userBean == null) {
            userBean = new ArrayList<UserBean>();
        }
        return this.userBean;
    }

}


package com.platform.modules.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepartmentBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "canceled",
    "code",
    "departmentid",
    "fullname",
    "shortname",
    "showorder",
    "subcompanyid",
    "supdepartmentid",
    "action",
    "lastChangdate"
})
public class DepartmentBean {

    @XmlElementRef(name = "_canceled", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> canceled;
    @XmlElementRef(name = "_code", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> code;
    @XmlElementRef(name = "_departmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentid;
    @XmlElementRef(name = "_fullname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fullname;
    @XmlElementRef(name = "_shortname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> shortname;
    @XmlElementRef(name = "_showorder", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> showorder;
    @XmlElementRef(name = "_subcompanyid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanyid;
    @XmlElementRef(name = "_supdepartmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> supdepartmentid;
    @XmlElementRef(name = "action", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> action;
    @XmlElementRef(name = "lastChangdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastChangdate;

    public JAXBElement<String> getCanceled() {
        return canceled;
    }

    public void setCanceled(JAXBElement<String> value) {
        this.canceled = value;
    }

    public JAXBElement<String> getCode() {
        return code;
    }

    public void setCode(JAXBElement<String> value) {
        this.code = value;
    }

    public JAXBElement<String> getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(JAXBElement<String> value) {
        this.departmentid = value;
    }

    public JAXBElement<String> getFullname() {
        return fullname;
    }

    public void setFullname(JAXBElement<String> value) {
        this.fullname = value;
    }

    public JAXBElement<String> getShortname() {
        return shortname;
    }

    public void setShortname(JAXBElement<String> value) {
        this.shortname = value;
    }

    public JAXBElement<String> getShoworder() {
        return showorder;
    }

    public void setShoworder(JAXBElement<String> value) {
        this.showorder = value;
    }

    public JAXBElement<String> getSubcompanyid() {
        return subcompanyid;
    }

    public void setSubcompanyid(JAXBElement<String> value) {
        this.subcompanyid = value;
    }

    public JAXBElement<String> getSupdepartmentid() {
        return supdepartmentid;
    }

    public void setSupdepartmentid(JAXBElement<String> value) {
        this.supdepartmentid = value;
    }

    public JAXBElement<String> getAction() {
        return action;
    }

    public void setAction(JAXBElement<String> value) {
        this.action = value;
    }

    public JAXBElement<String> getLastChangdate() {
        return lastChangdate;
    }

    public void setLastChangdate(JAXBElement<String> value) {
        this.lastChangdate = value;
    }

}

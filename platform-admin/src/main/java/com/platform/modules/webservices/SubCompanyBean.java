
package com.platform.modules.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubCompanyBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "canceled",
    "code",
    "fullname",
    "shortname",
    "showorder",
    "subcompanyid",
    "supsubcompanyid",
    "website",
    "action",
    "lastChangdate"
})
public class SubCompanyBean {

    @XmlElementRef(name = "_canceled", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> canceled;
    @XmlElementRef(name = "_code", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> code;
    @XmlElementRef(name = "_fullname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fullname;
    @XmlElementRef(name = "_shortname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> shortname;
    @XmlElementRef(name = "_showorder", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> showorder;
    @XmlElementRef(name = "_subcompanyid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanyid;
    @XmlElementRef(name = "_supsubcompanyid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> supsubcompanyid;
    @XmlElementRef(name = "_website", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> website;
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

    public JAXBElement<String> getSupsubcompanyid() {
        return supsubcompanyid;
    }

    public void setSupsubcompanyid(JAXBElement<String> value) {
        this.supsubcompanyid = value;
    }

    public JAXBElement<String> getWebsite() {
        return website;
    }

    public void setWebsite(JAXBElement<String> value) {
        this.website = value;
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

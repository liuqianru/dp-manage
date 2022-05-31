
package com.platform.modules.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JobTitleBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "code",
    "departmentid",
    "fullname",
    "jobcompetency",
    "jobdoc",
    "jobresponsibility",
    "jobtitleid",
    "jobtitleremark",
    "lastChangdate",
    "shortname",
    "action"
})
public class JobTitleBean {

    @XmlElementRef(name = "_code", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> code;
    @XmlElementRef(name = "_departmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentid;
    @XmlElementRef(name = "_fullname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fullname;
    @XmlElementRef(name = "_jobcompetency", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobcompetency;
    @XmlElementRef(name = "_jobdoc", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobdoc;
    @XmlElementRef(name = "_jobresponsibility", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobresponsibility;
    @XmlElementRef(name = "_jobtitleid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobtitleid;
    @XmlElementRef(name = "_jobtitleremark", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobtitleremark;
    @XmlElementRef(name = "_lastChangdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastChangdate;
    @XmlElementRef(name = "_shortname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> shortname;
    @XmlElementRef(name = "action", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> action;

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

    public JAXBElement<String> getJobcompetency() {
        return jobcompetency;
    }

    public void setJobcompetency(JAXBElement<String> value) {
        this.jobcompetency = value;
    }

    public JAXBElement<String> getJobdoc() {
        return jobdoc;
    }

    public void setJobdoc(JAXBElement<String> value) {
        this.jobdoc = value;
    }

    public JAXBElement<String> getJobresponsibility() {
        return jobresponsibility;
    }

    public void setJobresponsibility(JAXBElement<String> value) {
        this.jobresponsibility = value;
    }

    public JAXBElement<String> getJobtitleid() {
        return jobtitleid;
    }

    public void setJobtitleid(JAXBElement<String> value) {
        this.jobtitleid = value;
    }

    public JAXBElement<String> getJobtitleremark() {
        return jobtitleremark;
    }

    public void setJobtitleremark(JAXBElement<String> value) {
        this.jobtitleremark = value;
    }

    public JAXBElement<String> getLastChangdate() {
        return lastChangdate;
    }

    public void setLastChangdate(JAXBElement<String> value) {
        this.lastChangdate = value;
    }

    public JAXBElement<String> getShortname() {
        return shortname;
    }

    public void setShortname(JAXBElement<String> value) {
        this.shortname = value;
    }

    public JAXBElement<String> getAction() {
        return action;
    }

    public void setAction(JAXBElement<String> value) {
        this.action = value;
    }

}

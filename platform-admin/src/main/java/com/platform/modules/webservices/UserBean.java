
package com.platform.modules.webservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "accounttype",
    "assistantid",
    "bememberdate",
    "bepartydate",
    "birthday",
    "certificatenum",
    "createdate",
    "degree",
    "departmentcode",
    "departmentid",
    "departmentname",
    "dsporder",
    "educationlevel",
    "email",
    "enddate",
    "fax",
    "folk",
    "healthinfo",
    "height",
    "homeaddress",
    "islabouunion",
    "jobactivitydesc",
    "jobactivityid",
    "jobcall",
    "jobgroupid",
    "joblevel",
    "jobtitle",
    "lastChangdate",
    "lastname",
    "locationid",
    "loginid",
    "managerid",
    "maritalstatus",
    "mobile",
    "mobilecall",
    "nativeplace",
    "password",
    "policy",
    "regresidentplace",
    "residentplace",
    "seclevel",
    "sex",
    "startdate",
    "status",
    "subcompanycode",
    "subcompanyid1",
    "subcompanyname",
    "systemlanguage",
    "telephone",
    "tempresidentnumber",
    "userid",
    "weight",
    "workcode",
    "workroom"
})
public class UserBean {

    protected Integer accounttype;
    @XmlElementRef(name = "assistantid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> assistantid;
    @XmlElementRef(name = "bememberdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bememberdate;
    @XmlElementRef(name = "bepartydate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> bepartydate;
    @XmlElementRef(name = "birthday", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> birthday;
    @XmlElementRef(name = "certificatenum", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> certificatenum;
    @XmlElementRef(name = "createdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> createdate;
    @XmlElementRef(name = "degree", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> degree;
    @XmlElementRef(name = "departmentcode", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentcode;
    @XmlElementRef(name = "departmentid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentid;
    @XmlElementRef(name = "departmentname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> departmentname;
    protected Float dsporder;
    @XmlElementRef(name = "educationlevel", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> educationlevel;
    @XmlElementRef(name = "email", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> email;
    @XmlElementRef(name = "enddate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> enddate;
    @XmlElementRef(name = "fax", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> fax;
    @XmlElementRef(name = "folk", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> folk;
    @XmlElementRef(name = "healthinfo", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> healthinfo;
    @XmlElementRef(name = "height", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> height;
    @XmlElementRef(name = "homeaddress", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> homeaddress;
    @XmlElementRef(name = "islabouunion", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> islabouunion;
    @XmlElementRef(name = "jobactivitydesc", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobactivitydesc;
    @XmlElementRef(name = "jobactivityid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobactivityid;
    @XmlElementRef(name = "jobcall", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobcall;
    @XmlElementRef(name = "jobgroupid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobgroupid;
    @XmlElementRef(name = "joblevel", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> joblevel;
    @XmlElementRef(name = "jobtitle", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> jobtitle;
    @XmlElementRef(name = "lastChangdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastChangdate;
    @XmlElementRef(name = "lastname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> lastname;
    @XmlElementRef(name = "locationid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> locationid;
    @XmlElementRef(name = "loginid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> loginid;
    @XmlElementRef(name = "managerid", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> managerid;
    @XmlElementRef(name = "maritalstatus", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> maritalstatus;
    @XmlElementRef(name = "mobile", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mobile;
    @XmlElementRef(name = "mobilecall", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> mobilecall;
    @XmlElementRef(name = "nativeplace", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> nativeplace;
    @XmlElementRef(name = "password", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> password;
    @XmlElementRef(name = "policy", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> policy;
    @XmlElementRef(name = "regresidentplace", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> regresidentplace;
    @XmlElementRef(name = "residentplace", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> residentplace;
    @XmlElementRef(name = "seclevel", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> seclevel;
    @XmlElementRef(name = "sex", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> sex;
    @XmlElementRef(name = "startdate", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> startdate;
    @XmlElementRef(name = "status", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> status;
    @XmlElementRef(name = "subcompanycode", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanycode;
    @XmlElementRef(name = "subcompanyid1", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanyid1;
    @XmlElementRef(name = "subcompanyname", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> subcompanyname;
    @XmlElementRef(name = "systemlanguage", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> systemlanguage;
    @XmlElementRef(name = "telephone", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> telephone;
    @XmlElementRef(name = "tempresidentnumber", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> tempresidentnumber;
    protected Integer userid;
    @XmlElementRef(name = "weight", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> weight;
    @XmlElementRef(name = "workcode", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> workcode;
    @XmlElementRef(name = "workroom", namespace = "http://webservice.hrm.weaver", type = JAXBElement.class, required = false)
    protected JAXBElement<String> workroom;

    public Integer getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(Integer value) {
        this.accounttype = value;
    }

    public JAXBElement<String> getAssistantid() {
        return assistantid;
    }

    public void setAssistantid(JAXBElement<String> value) {
        this.assistantid = value;
    }

    public JAXBElement<String> getBememberdate() {
        return bememberdate;
    }

    public void setBememberdate(JAXBElement<String> value) {
        this.bememberdate = value;
    }

    public JAXBElement<String> getBepartydate() {
        return bepartydate;
    }

    public void setBepartydate(JAXBElement<String> value) {
        this.bepartydate = value;
    }

    public JAXBElement<String> getBirthday() {
        return birthday;
    }

    public void setBirthday(JAXBElement<String> value) {
        this.birthday = value;
    }

    public JAXBElement<String> getCertificatenum() {
        return certificatenum;
    }

    public void setCertificatenum(JAXBElement<String> value) {
        this.certificatenum = value;
    }

    public JAXBElement<String> getCreatedate() {
        return createdate;
    }

    public void setCreatedate(JAXBElement<String> value) {
        this.createdate = value;
    }

    public JAXBElement<String> getDegree() {
        return degree;
    }

    public void setDegree(JAXBElement<String> value) {
        this.degree = value;
    }

    public JAXBElement<String> getDepartmentcode() {
        return departmentcode;
    }

    public void setDepartmentcode(JAXBElement<String> value) {
        this.departmentcode = value;
    }

    public JAXBElement<String> getDepartmentid() {
        return departmentid;
    }

    public void setDepartmentid(JAXBElement<String> value) {
        this.departmentid = value;
    }

    public JAXBElement<String> getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(JAXBElement<String> value) {
        this.departmentname = value;
    }

    public Float getDsporder() {
        return dsporder;
    }

    public void setDsporder(Float value) {
        this.dsporder = value;
    }

    public JAXBElement<String> getEducationlevel() {
        return educationlevel;
    }

    public void setEducationlevel(JAXBElement<String> value) {
        this.educationlevel = value;
    }

    public JAXBElement<String> getEmail() {
        return email;
    }

    public void setEmail(JAXBElement<String> value) {
        this.email = value;
    }

    public JAXBElement<String> getEnddate() {
        return enddate;
    }

    public void setEnddate(JAXBElement<String> value) {
        this.enddate = value;
    }

    public JAXBElement<String> getFax() {
        return fax;
    }

    public void setFax(JAXBElement<String> value) {
        this.fax = value;
    }

    public JAXBElement<String> getFolk() {
        return folk;
    }

    public void setFolk(JAXBElement<String> value) {
        this.folk = value;
    }

    public JAXBElement<String> getHealthinfo() {
        return healthinfo;
    }

    public void setHealthinfo(JAXBElement<String> value) {
        this.healthinfo = value;
    }

    public JAXBElement<String> getHeight() {
        return height;
    }

    public void setHeight(JAXBElement<String> value) {
        this.height = value;
    }

    public JAXBElement<String> getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(JAXBElement<String> value) {
        this.homeaddress = value;
    }

    public JAXBElement<String> getIslabouunion() {
        return islabouunion;
    }

    public void setIslabouunion(JAXBElement<String> value) {
        this.islabouunion = value;
    }

    public JAXBElement<String> getJobactivitydesc() {
        return jobactivitydesc;
    }

    public void setJobactivitydesc(JAXBElement<String> value) {
        this.jobactivitydesc = value;
    }

    public JAXBElement<String> getJobactivityid() {
        return jobactivityid;
    }

    public void setJobactivityid(JAXBElement<String> value) {
        this.jobactivityid = value;
    }

    public JAXBElement<String> getJobcall() {
        return jobcall;
    }

    public void setJobcall(JAXBElement<String> value) {
        this.jobcall = value;
    }

    public JAXBElement<String> getJobgroupid() {
        return jobgroupid;
    }

    public void setJobgroupid(JAXBElement<String> value) {
        this.jobgroupid = value;
    }

    public JAXBElement<String> getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(JAXBElement<String> value) {
        this.joblevel = value;
    }

    public JAXBElement<String> getJobtitle() {
        return jobtitle;
    }

    public void setJobtitle(JAXBElement<String> value) {
        this.jobtitle = value;
    }

    public JAXBElement<String> getLastChangdate() {
        return lastChangdate;
    }

    public void setLastChangdate(JAXBElement<String> value) {
        this.lastChangdate = value;
    }

    public JAXBElement<String> getLastname() {
        return lastname;
    }

    public void setLastname(JAXBElement<String> value) {
        this.lastname = value;
    }

    public JAXBElement<String> getLocationid() {
        return locationid;
    }

    public void setLocationid(JAXBElement<String> value) {
        this.locationid = value;
    }

    public JAXBElement<String> getLoginid() {
        return loginid;
    }

    public void setLoginid(JAXBElement<String> value) {
        this.loginid = value;
    }

    public JAXBElement<String> getManagerid() {
        return managerid;
    }

    public void setManagerid(JAXBElement<String> value) {
        this.managerid = value;
    }

    public JAXBElement<String> getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(JAXBElement<String> value) {
        this.maritalstatus = value;
    }

    public JAXBElement<String> getMobile() {
        return mobile;
    }

    public void setMobile(JAXBElement<String> value) {
        this.mobile = value;
    }

    public JAXBElement<String> getMobilecall() {
        return mobilecall;
    }

    public void setMobilecall(JAXBElement<String> value) {
        this.mobilecall = value;
    }

    public JAXBElement<String> getNativeplace() {
        return nativeplace;
    }

    public void setNativeplace(JAXBElement<String> value) {
        this.nativeplace = value;
    }

    public JAXBElement<String> getPassword() {
        return password;
    }

    public void setPassword(JAXBElement<String> value) {
        this.password = value;
    }

    public JAXBElement<String> getPolicy() {
        return policy;
    }

    public void setPolicy(JAXBElement<String> value) {
        this.policy = value;
    }

    public JAXBElement<String> getRegresidentplace() {
        return regresidentplace;
    }

    public void setRegresidentplace(JAXBElement<String> value) {
        this.regresidentplace = value;
    }

    public JAXBElement<String> getResidentplace() {
        return residentplace;
    }

    public void setResidentplace(JAXBElement<String> value) {
        this.residentplace = value;
    }

    public JAXBElement<String> getSeclevel() {
        return seclevel;
    }

    public void setSeclevel(JAXBElement<String> value) {
        this.seclevel = value;
    }

    public JAXBElement<String> getSex() {
        return sex;
    }

    public void setSex(JAXBElement<String> value) {
        this.sex = value;
    }

    public JAXBElement<String> getStartdate() {
        return startdate;
    }

    public void setStartdate(JAXBElement<String> value) {
        this.startdate = value;
    }

    public JAXBElement<String> getStatus() {
        return status;
    }

    public void setStatus(JAXBElement<String> value) {
        this.status = value;
    }

    public JAXBElement<String> getSubcompanycode() {
        return subcompanycode;
    }

    public void setSubcompanycode(JAXBElement<String> value) {
        this.subcompanycode = value;
    }

    public JAXBElement<String> getSubcompanyid1() {
        return subcompanyid1;
    }

    public void setSubcompanyid1(JAXBElement<String> value) {
        this.subcompanyid1 = value;
    }

    public JAXBElement<String> getSubcompanyname() {
        return subcompanyname;
    }

    public void setSubcompanyname(JAXBElement<String> value) {
        this.subcompanyname = value;
    }

    public JAXBElement<String> getSystemlanguage() {
        return systemlanguage;
    }

    public void setSystemlanguage(JAXBElement<String> value) {
        this.systemlanguage = value;
    }

    public JAXBElement<String> getTelephone() {
        return telephone;
    }

    public void setTelephone(JAXBElement<String> value) {
        this.telephone = value;
    }

    public JAXBElement<String> getTempresidentnumber() {
        return tempresidentnumber;
    }

    public void setTempresidentnumber(JAXBElement<String> value) {
        this.tempresidentnumber = value;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer value) {
        this.userid = value;
    }

    public JAXBElement<String> getWeight() {
        return weight;
    }

    public void setWeight(JAXBElement<String> value) {
        this.weight = value;
    }

    public JAXBElement<String> getWorkcode() {
        return workcode;
    }

    public void setWorkcode(JAXBElement<String> value) {
        this.workcode = value;
    }

    public JAXBElement<String> getWorkroom() {
        return workroom;
    }

    public void setWorkroom(JAXBElement<String> value) {
        this.workroom = value;
    }

}

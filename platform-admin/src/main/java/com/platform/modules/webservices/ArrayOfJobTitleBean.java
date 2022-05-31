
package com.platform.modules.webservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfJobTitleBean", namespace = "http://webservice.hrm.weaver", propOrder = {
    "jobTitleBean"
})
public class ArrayOfJobTitleBean {

    @XmlElement(name = "JobTitleBean", nillable = true)
    protected List<JobTitleBean> jobTitleBean;

    public List<JobTitleBean> getJobTitleBean() {
        if (jobTitleBean == null) {
            jobTitleBean = new ArrayList<JobTitleBean>();
        }
        return this.jobTitleBean;
    }

}

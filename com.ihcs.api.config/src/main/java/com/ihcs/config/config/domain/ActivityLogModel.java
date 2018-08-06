/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "hcms_service_log_akses")
public class ActivityLogModel implements Serializable {

    @Id
    @Column(name = "LOG_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HCMS_SERVICE_LOG_AKSES_SEQ")
    @SequenceGenerator(name = "HCMS_SERVICE_LOG_AKSES_SEQ", sequenceName = "HCMS_SERVICE_LOG_AKSES_SEQ", allocationSize = 1, initialValue = 1)
    private String logid;

    public String getLogid() {
	return logid;
    }

    public void setLogid(String logid) {
	this.logid = logid;
    }

    @Column(name = "URL")
    private String url;

    @Column(name = "PARAM")
    private String param;

    @Column(name = "CREATED_DATE")
    private Date createddate;

    @Column(name = "IP")
    private String ip;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "START_TIME")
    private long starttime;

    @Column(name = "END_TIME")
    private long endtime;

    @Column(name = "TAKEN_TIME")
    private long takentime;

    public long getStarttime() {
	return starttime;
    }

    public void setStarttime(long starttime) {
	this.starttime = starttime;
    }

    public long getEndtime() {
	return endtime;
    }

    public void setEndtime(long endtime) {
	this.endtime = endtime;
    }

    public long getTakentime() {
	return takentime;
    }

    public void setTakentime(long takentime) {
	this.takentime = takentime;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getParam() {
	return param;
    }

    public void setParam(String param) {
	this.param = param;
    }

    public Date getCreateddate() {
	return createddate;
    }

    public void setCreateddate(Date createddate) {
	this.createddate = createddate;
    }

    public String getIp() {
	return ip;
    }

    public void setIp(String ip) {
	this.ip = ip;
    }

    public Integer getStatus() {
	return status;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

}

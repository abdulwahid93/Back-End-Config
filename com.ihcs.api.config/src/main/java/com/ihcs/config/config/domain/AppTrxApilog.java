/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andri Setiady
 */
@Entity
@Table(name = "APP_TRX_APILOG")
@XmlRootElement

public class AppTrxApilog implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @TableGenerator(
        name = "apilog",
        table = "app_trx_seq", 
	        pkColumnName = "seq_name", 
        valueColumnName = "seq_value", 
        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "apilog")
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOG_ID")
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_TRX_APILOG_SEQ")
    //@SequenceGenerator(name = "APP_TRX_APILOG_SEQ", sequenceName = "APP_TRX_APILOG_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal logId;
    @Size(max = 255)
    @Column(name = "URL")
    private String url;
    @Size(max = 1000)
    @Column(name = "PARAM")
    private String param;
    @Lob
    @Column(name = "REQUEST_BODY")
    private String requestBody;
    @Size(max = 50)
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Column(name = "STATUS")
    private BigInteger status;
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;
    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    public AppTrxApilog() {
    }

    public AppTrxApilog(BigDecimal logId) {
	this.logId = logId;
    }

    public BigDecimal getLogId() {
	return logId;
    }

    public void setLogId(BigDecimal logId) {
	this.logId = logId;
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

    public String getRequestBody() {
	return requestBody;
    }

    public void setRequestBody(String requestBody) {
	this.requestBody = requestBody;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

    public BigInteger getStatus() {
	return status;
    }

    public void setStatus(BigInteger status) {
	this.status = status;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public void setEndTime(Date endTime) {
	this.endTime = endTime;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (logId != null ? logId.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof AppTrxApilog)) {
	    return false;
	}
	AppTrxApilog other = (AppTrxApilog) object;
	if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ihcs.api.common.domain.AppTrxApilog[ logId=" + logId + " ]";
    }
    
}

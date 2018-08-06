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
@Table(name = "APP_TRX_USERACCESSLOG")

public class AppTrxUseraccesslog implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @TableGenerator(
        name = "useraccesslog",
        table = "app_trx_seq", 
	        pkColumnName = "seq_name", 
        valueColumnName = "seq_value", 
        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "useraccesslog")
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOG_ID")
   // @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_TRX_USERACCESSLOG_SEQ")
    //@SequenceGenerator(name = "APP_TRX_USERACCESSLOG_SEQ", sequenceName = "APP_TRX_USERACCESSLOG_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal logId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "USER_ID")
    private BigInteger userId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MODUL_ID")
    private BigInteger modulId;
    @Size(max = 50)
    @Column(name = "IP_ADDRESS")
    private String ipAddress;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "ACTION_LOG_ID")
    private BigInteger actionLogId;
    @Size(max = 255)
    @Column(name = "LOG_NOTE")
    private String logNote;
    @Column(name = "STATUS")
    private BigInteger status;
    @Size(max = 255)
    @Column(name = "ACCESS_PATH")
    private String accessPath;

    public AppTrxUseraccesslog() {
    }

    public AppTrxUseraccesslog(BigDecimal logId) {
	this.logId = logId;
    }

    public AppTrxUseraccesslog(BigDecimal logId, BigInteger userId, BigInteger modulId) {
	this.logId = logId;
	this.userId = userId;
	this.modulId = modulId;
    }

    public BigDecimal getLogId() {
	return logId;
    }

    public void setLogId(BigDecimal logId) {
	this.logId = logId;
    }

    public BigInteger getUserId() {
	return userId;
    }

    public void setUserId(BigInteger userId) {
	this.userId = userId;
    }

    public BigInteger getModulId() {
	return modulId;
    }

    public void setModulId(BigInteger modulId) {
	this.modulId = modulId;
    }

    public String getIpAddress() {
	return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
	this.ipAddress = ipAddress;
    }

    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }

    public BigInteger getActionLogId() {
	return actionLogId;
    }

    public void setActionLogId(BigInteger actionLogId) {
	this.actionLogId = actionLogId;
    }

    public String getLogNote() {
	return logNote;
    }

    public void setLogNote(String logNote) {
	this.logNote = logNote;
    }

    public BigInteger getStatus() {
	return status;
    }

    public void setStatus(BigInteger status) {
	this.status = status;
    }

    public String getAccessPath() {
	return accessPath;
    }

    public void setAccessPath(String accessPath) {
	this.accessPath = accessPath;
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
	if (!(object instanceof AppTrxUseraccesslog)) {
	    return false;
	}
	AppTrxUseraccesslog other = (AppTrxUseraccesslog) object;
	if ((this.logId == null && other.logId != null) || (this.logId != null && !this.logId.equals(other.logId))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ihcs.api.common.domain.AppTrxUseraccesslog[ logId=" + logId + " ]";
    }
    
}

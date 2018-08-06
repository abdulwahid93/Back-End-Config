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
@Table(name = "APP_TRX_SESSION")

public class AppTrxSession implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @TableGenerator(
        name = "session",
        table = "app_trx_seq", 
	        pkColumnName = "seq_name", 
        valueColumnName = "seq_value", 
        allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "session")
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "APP_TRX_SESSION_SEQ")
    //@SequenceGenerator(name = "APP_TRX_SESSION_SEQ", sequenceName = "APP_TRX_SESSION_SEQ", allocationSize = 1, initialValue = 1)
    private BigDecimal id;
    @Column(name = "USER_ID")
    private BigDecimal userId;
    @Column(name = "PERSON_ID")
    private BigInteger personId;
    @Size(max = 255)
    @Column(name = "SESSION_ID")
    private String sessionId;
    @Column(name = "IS_ACTIVE")
    private Short isActive;
    @Column(name = "DATE_CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;

    public AppTrxSession() {
    }

    public AppTrxSession(BigDecimal id) {
	this.id = id;
    }

    public BigDecimal getId() {
	return id;
    }

    public void setId(BigDecimal id) {
	this.id = id;
    }

    public BigDecimal getUserId() {
	return userId;
    }

    public void setUserId(BigDecimal userId) {
	this.userId = userId;
    }

    public BigInteger getPersonId() {
	return personId;
    }

    public void setPersonId(BigInteger personId) {
	this.personId = personId;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public Short getIsActive() {
	return isActive;
    }

    public void setIsActive(Short isActive) {
	this.isActive = isActive;
    }

    public Date getDateCreated() {
	return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
	this.dateCreated = dateCreated;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (id != null ? id.hashCode() : 0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are not set
	if (!(object instanceof AppTrxSession)) {
	    return false;
	}
	AppTrxSession other = (AppTrxSession) object;
	if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "com.ihcs.api.common.domain.AppTrxSession[ id=" + id + " ]";
    }
    
}

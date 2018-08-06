/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.dao;


import com.ihcs.config.config.TransactionOFM;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
public class CekSessionDao {
	
    @Autowired
    @Qualifier("OFMSessionFactory")
    private SessionFactory OFMDB;
   
	
    @TransactionOFM
    public boolean cekSession(BigDecimal userId,String sessionId){
	String sql = "SELECT  a FROM AppTrxSession a WHERE a.userId = :userId AND a.sessionId = :sessionId AND a.isActive = 1";

	Query query = OFMDB.getCurrentSession().createQuery(sql);
	query.setParameter("userId", userId);
	query.setParameter("sessionId", sessionId);
	
	

	return !query.list().isEmpty();
		
    }
	
	
	
}

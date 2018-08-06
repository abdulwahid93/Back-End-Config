/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.daoimpl;


import com.ihcs.config.config.TransactionOFM;
import com.ihcs.config.config.domain.ActivityLogModel;
import com.ihcs.config.config.domain.AppTrxApilog;
import com.ihcs.config.config.domain.AppTrxUseraccesslog;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.ihcs.config.dao.ApiLogDao;

@Repository
public class ApiLogDaoImpl implements  ApiLogDao{
    final static Logger logger = Logger.getLogger(ApiLogDaoImpl.class.getName());
    
    @Autowired
    @Qualifier("OFMSessionFactory")
    private SessionFactory OFMDB;

    @TransactionOFM
    public void insertLog(ActivityLogModel tbllogmodel) {

	// OFMDB.getCurrentSession().saveOrUpdate(tbllogmodel);
	String sql = " insert into hcms_service_log_akses(url,param,created_date,ip,start_time,end_time,taken_time) "
		+ "values(:URL,:PARAM,:CREATED_DATE,:IP,:starttime,:endtime,:takentime)";

	Query query = OFMDB.getCurrentSession().createSQLQuery(sql);
	query.setParameter("URL", tbllogmodel.getUrl());
	query.setParameter("PARAM", tbllogmodel.getParam());
	query.setParameter("CREATED_DATE", tbllogmodel.getCreateddate());
	query.setParameter("IP", tbllogmodel.getIp());
	query.setParameter("starttime", tbllogmodel.getStarttime());
	query.setParameter("endtime", tbllogmodel.getEndtime());
	query.setParameter("takentime", tbllogmodel.getTakentime());
	//query.setParameter("STATUS", tbllogmodel.getStatus());

	System.out.println("&&&&& >>>" + tbllogmodel.getCreateddate());

	query.executeUpdate();
    }

    @TransactionOFM
    //@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public void inserApitLog(AppTrxApilog appTrxApilog) {

	OFMDB.getCurrentSession().save(appTrxApilog);

    }
    
     @TransactionOFM
    
    public void insertUserAccessLog(AppTrxUseraccesslog appTrxUseraccesslog) {

	OFMDB.getCurrentSession().save(appTrxUseraccesslog);

    }
    
     @TransactionOFM
    
    public void keepAlive() {

	OFMDB.getCurrentSession().createQuery(" from AppTrxUseraccesslog where logId=1 ");

    }
}

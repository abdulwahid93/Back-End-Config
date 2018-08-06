/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.dao;

import com.ihcs.config.config.domain.ActivityLogModel;
import com.ihcs.config.config.domain.AppTrxApilog;
import com.ihcs.config.config.domain.AppTrxUseraccesslog;



/**
 *
 * @author Andri Setiady
 */
public interface ApiLogDao {
    
    public void insertLog(ActivityLogModel tbllogmodel);
    public void inserApitLog(AppTrxApilog appTrxApilog);
    public void insertUserAccessLog(AppTrxUseraccesslog appTrxUseraccesslog);
    public void keepAlive();
    
}

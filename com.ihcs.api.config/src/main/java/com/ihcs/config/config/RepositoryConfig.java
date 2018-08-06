/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.hibernate.Interceptor;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

@Configuration
@ComponentScan(basePackages = {"com.ihcs"})
@EnableTransactionManagement

public class RepositoryConfig {

    final static Logger logger = Logger.getLogger(RepositoryConfig.class.getName());

//CONFIGURASI FOR DATABASE OFM ---------------------------------------------------------------------  
    @Autowired
    private Environment env;

//    @Bean(name = "OFMDataSource")
//    public DataSource OFMDataSource() {
//	System.out.println("**********REPOS CONFIG**********");
//
//	try {
//	    Class.forName("oracle.jdbc.driver.OracleDriver");
//	} catch (ClassNotFoundException ex) {
//
//	}
//
//	String url = env.getProperty("ofmdb.url");
//	String username = env.getProperty("ofmdb.uname");
//	String password = env.getProperty("ofmdb.pass");
//	System.out.println("Con ------------ >> :" + env.getProperty("ofmdb.url").toString());
//
////        ConnectionFactory cf = new DriverManagerConnectionFactory(url, username, password);
////        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
////        GenericObjectPool<PoolableConnection> cp = new GenericObjectPool<>(pcf);
////
////        pcf.setPool(cp);
////		
////        return new PoolingDataSource<>(cp);
//	BasicDataSource bds = new BasicDataSource();
//	bds.setUrl(url);
//	bds.setUsername(username);
//	bds.setPassword(password);
//
//	return bds;
//    }
    //@Bean(name = "dataSource")
    //public DataSource getDataSource(){
    @Bean(name = "OFMDataSource")
    public DataSource OFMDataSource() {
	logger.info("**********REPOS CONFIG**********");

	DataSource dataSource = null;
	String driverClass = env.getProperty("datasource.driver");
	String jndiName = env.getProperty("datasource.jndi");

	if (!StringUtils.isEmpty(driverClass)) {
	    try {
		Class.forName(driverClass);
	    } catch (Exception e) {
		logger.error("Failed to load JDBC Driver " + driverClass, e);
	    }
	}

	logger.info("**********JNDI**********" + jndiName);

	if (jndiName != null && !jndiName.trim().isEmpty()) {
	    try {
		Context ctx = new InitialContext();
		dataSource = (DataSource) ctx.lookup(jndiName);
		
		logger.info("**********REPOS CONFIG - CREATE JNDI CONNECTION **********");
	    } catch (NamingException ne) {
		logger.warn("Could not find JNDI : " + jndiName + " fallback to local settings");

	    }
	}

	if (dataSource == null) {
	    dataSource = createDataSource();
	}

	if (dataSource == null) {
	    throw new RuntimeException("DataSource is not configured properly. Check your datasource/JNDI settings");
	}

	logger.info("Bean dataSource initialization done");

	return dataSource;
    }

    private DataSource createDataSource() {
	
	logger.info("**********REPOS CONFIG - CREATE LOCAL CONNECTION **********");

	String url = env.getProperty("ofmdb.url");
	String username = env.getProperty("ofmdb.uname");
	String password = env.getProperty("ofmdb.pass");
	int init=Integer.parseInt(env.getProperty("ofmdb.init"));
	int maxTot=Integer.parseInt(env.getProperty("ofmdb.maxTot"));
	int maxIdle=Integer.parseInt(env.getProperty("ofmdb.maxIdle"));
	long maxWait=Long.parseLong(env.getProperty("ofmdb.maxWait"));
	
//	System.out.println("Con ------------ >> :" + env.getProperty("ofmdb.url").toString());
	logger.info("Connection ------------ >> :" + env.getProperty("ofmdb.url").toString());

	if (StringUtils.isEmpty(url)) {
	    return null;
	}

//		ConnectionFactory cf = new DriverManagerConnectionFactory(url, username, password);
//		PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
//		GenericObjectPool<PoolableConnection> cp = new GenericObjectPool<>(pcf);
//		
//		//cp.setMaxIdle(PropertyUtils.getInt("connection.maxIdle", 1800));
//		//cp.setMaxTotal(PropertyUtils.getInt("connection.maxTotal", 100));
//		pcf.setPool(cp);
//		
//		return new PoolingDataSource<>(cp);
	BasicDataSource bds = new BasicDataSource();
	
	
	bds.setInitialSize(init);
	bds.setMaxTotal(maxTot);
	bds.setMaxIdle(maxIdle);
	bds.setMaxWaitMillis(maxWait);

	bds.setUrl(url);
	bds.setUsername(username);
	bds.setPassword(password);

	return bds;
    }

    @Bean(name = "OFMSessionFactory")
    public LocalSessionFactoryBean OFMsessionFactoryBean(DataSource OFMDataSource) {
	LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
        sfb.setDataSource(OFMDataSource);
	sfb.setPackagesToScan("com.ihcs");

	Properties props = sfb.getHibernateProperties();

	props.put("hibernate.format_sql", true);
	props.put("hibernate.show_sql", true);
	props.put("hibernate.jdbc.batch_size", 20);
	props.put("hibernate.dialect", "org.hibernate.dialect.Oracle9iDialect");
        
	return sfb;
    }

    @Bean(name = "OFMTrxMgr")
    public PlatformTransactionManager getHibernateTransactionManagerOFM(SessionFactory OFMSessionFactory) {
        HibernateTransactionManager trxMgr = new HibernateTransactionManager(OFMSessionFactory);
        
	return trxMgr;
    }
    //END CONFIG FOR DATABASE OFM ---------------------------------------------------------------------        

//CONFIGURASI FOR DATABASE PRODHR ---------------------------------------------------------------------  
    @Bean(name = "PRODHRDataSource")
    public DataSource PRODHRDataSource() {

	try {
	    Class.forName("oracle.jdbc.driver.OracleDriver");
	} catch (ClassNotFoundException ex) {

	}

	String url = env.getProperty("prodhr.url");
	String username = env.getProperty("prodhr.uname");
	String password = env.getProperty("prodhr.pass");

//        ConnectionFactory cf = new DriverManagerConnectionFactory(url, username, password);
//        PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
//        GenericObjectPool<PoolableConnection> cp = new GenericObjectPool<>(pcf);
//
//        pcf.setPool(cp);
//
//        return new PoolingDataSource<>(cp);
	BasicDataSource bds = new BasicDataSource();
	bds.setUrl(url);
	bds.setUsername(username);
	bds.setPassword(password);

	return bds;
    }

    @Bean(name = "PRODHRSessionFactory")
    public LocalSessionFactoryBean PRODHRsessionFactoryBean(DataSource PRODHRDataSource) {

	LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
	sfb.setDataSource(PRODHRDataSource);
	sfb.setPackagesToScan("com.ihcs");

	Properties props = sfb.getHibernateProperties();

	props.put("hibernate.format_sql", true);
	props.put("hibernate.show_sql", true);
	props.put("hibernate.dialect", "org.hibernate.dialect.Oracle9iDialect");
	return sfb;
//        try{
//            Statement stmt = PRODHRDataSource.getConnection().createStatement();
//            ResultSet rs = stmt.executeQuery("select 'OKE' status from dual");
//            if(rs.next()){
//                System.out.println("STATUS = " + rs.getString("STATUS"));
//            }
//            rs.close();
//            stmt.close();
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }

    }

    @Bean(name = "PRODHRTrxMgr")
    public PlatformTransactionManager getHibernateTransactionManagerPRODHR(SessionFactory PRODHRSessionFactory) {

	HibernateTransactionManager trxMgr = new HibernateTransactionManager(PRODHRSessionFactory);

	return trxMgr;

    }
    //END CONFIG FOR DATABASE PRODHR ---------------------------------------------------------------------     

    @Bean
    public JavaMailSenderImpl javaMailSender() {
	JavaMailSenderImpl MailSender = new JavaMailSenderImpl();
	Properties javaMailProperties = new Properties();
	javaMailProperties.put("mail.smtp.user", "infohct");
	javaMailProperties.put("mail.smtp.password", "passw0rd");

	javaMailProperties.put("mail.smtp.auth", false);
	javaMailProperties.put("mail.smtp.starttls.enable", false);
	javaMailProperties.put("mail.smtp.starttls.required", false);
	javaMailProperties.put("mail.smtp.host", "192.168.45.85");
	javaMailProperties.put("mail.smtp.port", "25");

	MailSender.setJavaMailProperties(javaMailProperties);
	return MailSender;
    }
    
}

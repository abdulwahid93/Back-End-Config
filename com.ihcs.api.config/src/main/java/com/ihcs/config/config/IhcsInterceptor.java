/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ihcs.config.config;

import com.ihcs.config.config.domain.AppTrxApilog;
import com.ihcs.config.dao.ApiLogDao;
import com.ihcs.config.dao.CekSessionDao;
import com.ihcs.config.exception.IhcsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;

@Component
public class IhcsInterceptor implements HandlerInterceptor {

    final static Logger logger = Logger.getLogger(IhcsInterceptor.class.getName());

    @Autowired
    private CekSessionDao cekSessionDao;
    
    @Autowired ApiLogDao logDao;
    
    @Override
    public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler) throws Exception {

	Date startTime = new Date();//System.currentTimeMillis();
	request.setAttribute("startTime", startTime);
	System.out.println("akses " + request.getServletPath());

	if (request.getMethod().equals("GET") || request.getMethod().equals("POST")) {

	    if (request.getServletPath().equalsIgnoreCase("/login") 
                    || request.getServletPath().equalsIgnoreCase("/sesionFailed") 
                    || request.getServletPath().equalsIgnoreCase("/proxy") 
                    || request.getServletPath().equalsIgnoreCase("/cekSession") 
                    || request.getServletPath().equalsIgnoreCase("/insertApiLog") 
                    || request.getServletPath().equalsIgnoreCase("/getLanguage") 
                    || request.getServletPath().equalsIgnoreCase("/insertUserAccessLog")
                    || request.getServletPath().startsWith("/v2/api-docs")
                    || request.getServletPath().startsWith("/swagger-ui.html")
                    || request.getServletPath().startsWith("/configuration") 
                    || request.getServletPath().startsWith("/swagger-resource") 
                    || request.getServletPath().startsWith("/swagger-ui")
                    || request.getServletPath().startsWith("/getProfilePictureByEmpId")
		    || request.getServletPath().equalsIgnoreCase("/getactivetheme")
		    || request.getServletPath().equalsIgnoreCase("/getslider")
			|| request.getServletPath().equalsIgnoreCase("/getActiveMstPwd")) {
		   
			    

		return true;

	    } else {

		if (request.getHeader("userid") == null || request.getHeader("sessionid") == null) {

		    logger.info(request.getContextPath() + request.getServletPath() + " - " + request.getMethod() + " - " + "Invalid Parameter");
		    return false;
		}

		BigDecimal userid = new BigDecimal(request.getHeader("userid"));
		String sessionid = request.getHeader("sessionid");

		try {

		    if (cekSessionDao.cekSession(userid, sessionid)) {

			return true;

		    } else {

			logger.error(request.getContextPath() + request.getServletPath() + " - " + request.getMethod() + " - " + request.getHeader("userid") + " - " + "Session Not Aktif");
			//response.sendRedirect("sesionFailed");
			return false;

		    }
		} catch (Exception e) {
		    e.printStackTrace();
		    logger.error(request.getContextPath() + request.getServletPath() + " - " + request.getMethod() + " - " + request.getQueryString());
		    logger.error(e.getMessage());
		    throw new IhcsException("ERROR IhcsInterceptor");

		}
	    }
	}
	return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler,
	    ModelAndView modelAndView) throws Exception {

	Date startTime = (Date) request.getAttribute("startTime");
	boolean logApi = false;
	if (request.getAttribute("logAPI") != null) {
	    logApi = (boolean) request.getAttribute("logAPI");
	}

	//if (!request.getServletPath().equalsIgnoreCase("/insertApiLog") ) {
	//if (!request.getServletPath().equalsIgnoreCase("/cekSession")) {
	if(logApi){
	    if (request.getMethod().equals("GET") || request.getMethod().equals("POST")) {
		String param = null;
		String body = null;
		String key = null;
		String value = null;
		int count = 0;

		//read body
//		if ("application/json".equals(request.getContentType())) {
//
//		    param = "application/json";
//		    //		    StringBuffer jb = new StringBuffer();
//		    //		    String line = null;
//		    //                    BufferedReader reader = request.getReader();
//		    //		     while ((line = reader.readLine()) != null)
//		    //			jb.append(line);
//		    //		     
//		    //		     body=jb.toString();
//		}
		//read parameter
		Enumeration keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
		    key = (String) keys.nextElement();
		    System.out.println("key : " + key);

		    if (!"_".equals(key)) {

			if (key.equals("password")) {
			    value = "*****";
			} else {

			    value = request.getParameter(key);
			    System.out.println("val :" + value);

			}

			if (count == 0) {
			    param = "\"" + key + "\":" + value;

			} else {

			    param = param + "," + "\"" + key + "\":" + value;
			}
		    }

		    count = count + 1;
		}

		String header = request.getHeader("userid") + "- " + request.getHeader("sessionid");
		String Data = "Header:" + header + "; " + "Param:" + param + "Body:" + body;

		Date myDate = new Date();
		Date endTime = new Date();//System.currentTimeMillis();
		// long takenTime = endTime - startTime;

		//	    ActivityLogModel tbllogModel = new ActivityLogModel();
		//	    tbllogModel.setUrl(request.getRequestURL().toString());
		//	    tbllogModel.setParam(Data);
		//	    tbllogModel.setCreateddate(myDate);
		//	    tbllogModel.setStarttime(startTime);
		//
		//	    tbllogModel.setEndtime(endTime);
		//	    tbllogModel.setTakentime(takenTime);
		//	    tbllogModel.setIp(request.getRemoteHost());
		System.out.println("Log It>>>>");

		AppTrxApilog appTrxApilog = new AppTrxApilog();
		appTrxApilog.setUrl(request.getRequestURL().toString());
		appTrxApilog.setParam(Data);
		appTrxApilog.setCreatedDate(myDate);
		appTrxApilog.setStartTime(startTime);
		appTrxApilog.setEndTime(endTime);
		appTrxApilog.setIpAddress(request.getRemoteHost());
		logDao.inserApitLog(appTrxApilog);
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("userid", request.getHeader("userid"));
//		headers.set("sessionid", request.getHeader("sessionid"));
//			
//		
//		//String uri="http://localhost:8080/logapi/insertLog";
//		String uri = "http://localhost:" + request.getLocalPort() + "/com.ihcs.api.log/insertApiLog";
//		RestTemplate resttemplate = new RestTemplate();
//		
////		//HttpEntity entity = new HttpEntity(tbllogModel, headers);
//		HttpEntity entityx = new HttpEntity(appTrxApilog, headers);
//		resttemplate.postForObject(uri, entityx, String.class);
////		//resttemplate.postForObject(uri, handler, responseType)
		 
	    }
	}
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

//            if (request.getMethod().equals("GET") || request.getMethod().equals("POST")) {
//            	long startTime = (Long) request.getAttribute("startTime");
//		logger.info(request.getContextPath() + request.getServletPath() + " - " + request.getMethod() + " - " + request.getHeader("userid") + " - :: Time Taken= " + (System.currentTimeMillis() - startTime));
//		}
    }
}

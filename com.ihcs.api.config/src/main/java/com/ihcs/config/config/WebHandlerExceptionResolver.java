package com.ihcs.config.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihcs.config.exception.IhcsException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author Syamsul Bachri
 */
public class WebHandlerExceptionResolver implements HandlerExceptionResolver {

    private final Log logger = LogFactory.getLog(WebHandlerExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception excptn) {
	PrintWriter pw = null;
	response.setContentType("application/json");
	try {
	    pw = response.getWriter();
	} catch (IOException ex) {
	    logger.error(o, ex);
	}

	Map<String, Object> struct = new HashMap<>();
	struct.put("isError", true);

	if (excptn instanceof IhcsException) {
	    struct.put("message", excptn.getMessage());
	} else {
	    struct.put("message", "Application is unavailable. Please try again later.");
	    logger.error(o, excptn);
	}

	if (pw != null) {
	    ObjectMapper om = new ObjectMapper();

	    try {
		pw.write(om.writeValueAsString(struct));
	    } catch (Exception x) {
	    }
	}
	return null;
    }

}

/**
 * 
 */
package com.java.main.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.java.main.util.HttpClientUtils;


/**
 * @author cuijin
 * 
 */
@Controller
@RequestMapping("/redirect")
public class RedirectController {
	
	private final static Logger logger = LoggerFactory.getLogger(RedirectController.class);
	
	@Autowired
	private HttpClientUtils clientUtil;
	
	
	
	/**
	 * 转发请求
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doPost", method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpServletRequest request){
		logger.info("do post");
		Map<String,String[]> params = request.getParameterMap();
		String redirectUrl = request.getParameter("redirectUrl");
		
		Map<String,String> redirectParams = new HashMap<String,String>();
		for(String key : params.keySet()){
			if(!"redirectUrl".equals(key)){
				String[] value = (String[])params.get(key);
				redirectParams.put(key, value[0]);
				logger.info("params :"+ key + ":" + value[0]);
			}
		}
		
		return clientUtil.sendRequest(redirectUrl, redirectParams);
	}
	
	@RequestMapping(value = "/other")
	@ResponseBody
	public String other(HttpServletRequest request){
		return "success";
	}

}

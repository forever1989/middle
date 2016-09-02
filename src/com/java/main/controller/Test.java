/**
 * 
 */
package com.java.main.controller;

import java.util.HashMap;
import java.util.Map;

import com.java.main.util.HttpClientUtils;

/**
 * @author cuijin
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<String,String> params = new HashMap<String,String>();
		
		params.put("name", "cj");
		params.put("sign", "sign");
		params.put("age", "18");
		params.put("type", "post");
		params.put("redirectUrl", "https://pay.chexiang.com/cardCallBackPay/notify.htm");
		
		String url = "http://localhost:8080/middle/redirect/doPost.htm";
		HttpClientUtils clientUtils = new HttpClientUtils();
		String result = clientUtils.sendRequest(url, params);
		System.out.println(result);
	}

}

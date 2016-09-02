/**
 * 
 */
package com.java.main.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author cuijin
 *
 */
@Component
public class HttpClientUtils {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	/**
	 * 创建https客户端
	 * @return
	 */
	public  CloseableHttpClient createSSLClientDefault(){
		try {
             SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                 //信任所有
                 public boolean isTrusted(X509Certificate[] chain,
                                 String authType) throws CertificateException {
                     return true;
                 }
             }).build();
             SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
             
             return HttpClients.custom().setSSLSocketFactory(sslsf).build();
         } catch (KeyManagementException e) {
             e.printStackTrace();
         } catch (NoSuchAlgorithmException e) {
             e.printStackTrace();
         } catch (KeyStoreException e) {
             e.printStackTrace();
         }
         return  HttpClients.createDefault();
		}
	
	/**
	 * 转发请求，并返回字符串
	 * @param url
	 * @param params
	 * @return
	 */
	public String sendRequest(String redirectUrl, Map<String,String> params){
		
		CloseableHttpClient client = createSSLClientDefault();
	    // 创建httppost    
	    HttpPost httppost = new HttpPost(redirectUrl);
	    //参数
	    List<NameValuePair> formparams = new ArrayList<NameValuePair>();
	    
	    //遍历并设置参数
	    for(String key : params.keySet()){
	    	BasicNameValuePair nameValue = new BasicNameValuePair(key,params.get(key));
	    	formparams.add(nameValue);
	    }
	    
	    UrlEncodedFormEntity uefEntity;  
	       try {  
	           uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
	           httppost.setEntity(uefEntity);  
		       logger.info("executing request " + httppost.getURI());
		       
	           CloseableHttpResponse response = client.execute(httppost);  
	           try {  
	               HttpEntity entity = response.getEntity(); 
	               
	               if (entity != null) {  
	            	   
	                   logger.info("--------------------------------------");
	                   String responseString = EntityUtils.toString(entity, "UTF-8");
	                   logger.info(responseString);
	                   logger.info("--------------------------------------");
	                   
	                   return responseString;  
	               }  
	           } finally {  
	               response.close();  
	           }  
	       } catch (ClientProtocolException e) {  
	    	   logger.error(e.getMessage());
	    	   e.printStackTrace();
	       } catch (UnsupportedEncodingException e1) {  
	    	   logger.error(e1.getMessage());
	    	   e1.printStackTrace();
	       } catch (IOException e2) {  
	    	   logger.error(e2.getMessage());
	    	   e2.printStackTrace();
	       } finally {}
	    
		return "error";
	}
	
}

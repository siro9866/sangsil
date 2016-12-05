package com.mee.sangsil.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mee.sangsil.common.controller.LoginController;

public class HttpsClientWithoutValidation {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpsClientWithoutValidation.class);
	
	 /** 
		*	
		* @param urlString 
		* @throws IOException 
		* @throws NoSuchAlgorithmException 
		* @throws KeyManagementException 
		*/	
	public void https(String urlString) throws IOException, NoSuchAlgorithmException, KeyManagementException {	
			
		// Get HTTPS URL connection	
		URL url = new URL(urlString);		
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();	
			
		// Set Hostname verification	
		conn.setHostnameVerifier(new HostnameVerifier() {	
		 @Override	
		 public boolean verify(String hostname, SSLSession session) {	
			// Ignore host name verification. It always returns true.	
			return true;	
		 }	
			 	
		});	
			
		// SSL setting	
		SSLContext context = SSLContext.getInstance("TLS");	
		context.init(null, null, null);	// No validation for now	
		conn.setSSLSocketFactory(context.getSocketFactory());	
			
		// Connect to host	
		conn.connect();	
		conn.setInstanceFollowRedirects(true);	
			
		// Print response from host	
		InputStream in = conn.getInputStream();	
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));	
		String line = null;	
		logger.debug("S:========HTTPS 응답결과================================================");
		while ((line = reader.readLine()) != null) {
		 System.out.printf("%s\n", line);	
		}	
		logger.debug("E:========HTTPS 응답결과================================================");	
		reader.close();	
	}	
	
	/**
	 * 결과값 리턴받
	 * @param urlString
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 */
	public StringBuffer getHttps(String urlString) throws IOException, NoSuchAlgorithmException, KeyManagementException {	
		
		// Get HTTPS URL connection	
		URL url = new URL(urlString);		
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();	
			
		// Set Hostname verification	
		conn.setHostnameVerifier(new HostnameVerifier() {	
		 @Override	
		 public boolean verify(String hostname, SSLSession session) {	
			// Ignore host name verification. It always returns true.	
			return true;	
		 }	
			 	
		});	
			
		// SSL setting	
		SSLContext context = SSLContext.getInstance("TLS");	
		context.init(null, null, null);	// No validation for now	
		conn.setSSLSocketFactory(context.getSocketFactory());	
		
		// Connect to host	
		conn.connect();	
		conn.setInstanceFollowRedirects(true);	
			
		StringBuffer result = new StringBuffer();
		
		// Print response from host	
		InputStream in = conn.getInputStream();	
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;	
		logger.debug("S:========HTTPS 응답결과================================================");
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		logger.debug("result:"+result.toString());
		logger.debug("E:========HTTPS 응답결과================================================");	
		reader.close();
		return result;
	}	
	
	
	public StringBuffer getHttpProp(String urlString, String prop) throws IOException, NoSuchAlgorithmException, KeyManagementException {	
		
		// Get HTTPS URL connection	
		URL url = new URL(urlString);		
		HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();	
			
		// Set Hostname verification	
		conn.setHostnameVerifier(new HostnameVerifier() {	
		 @Override	
		 public boolean verify(String hostname, SSLSession session) {	
			// Ignore host name verification. It always returns true.	
			return true;	
		 }	
			 	
		});	
			
		// SSL setting	
		SSLContext context = SSLContext.getInstance("TLS");	
		context.init(null, null, null);	// No validation for now	
		conn.setSSLSocketFactory(context.getSocketFactory());	
		
		//네이버 로그인 정보 가져오기 헤더값설정
		conn.setRequestProperty("Authorization", prop);
		
		// Connect to host	
		conn.connect();	
		conn.setInstanceFollowRedirects(true);	
			
		StringBuffer result = new StringBuffer();
		
		// Print response from host	
		InputStream in = conn.getInputStream();	
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;	
		logger.debug("S:========HTTPS 응답결과================================================");
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		logger.debug("result:"+result.toString());
		logger.debug("E:========HTTPS 응답결과================================================");	
		reader.close();
		return result;
	}	
	 /** 
		*	
		* @param args 
		* @throws Exception 
		*/	
//	 public static void main(String[] args) throws Exception {	
//		HttpsClientWithoutValidation test = new HttpsClientWithoutValidation();	
//		test.getHttps("https://www.google.com");	
//	 }	

	
	
}

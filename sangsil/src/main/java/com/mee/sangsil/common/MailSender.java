package com.mee.sangsil.common;

	/*
	 source from javabrain.co.kr 

	*/

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

	public class MailSender {
	  
	  public MailSender() throws UnsupportedEncodingException {
	   
	  }
	  
	  public void mailSenderChoiced() throws UnsupportedEncodingException {

	  
	  String to = "siro-9@hanmail.net";   //받는 사람
	  String from = "siro9@naver.com";   //보내는 사람
	  String host = "smtp.naver.com";     //Gmail: smtp.gmail.com
	  String msgText = "";
	   
	   boolean debug = Boolean.valueOf("true").booleanValue();
	   Properties props = new Properties();
	   props.put("mail.smtp.starttls.enable", "true"); //Gmail의 경우 반드시 true로 세팅해야 한다.
	   props.put("mail.smtp.host", host);
	   props.put("mail.smtp.auth","true");    //반드시 프로퍼티에 세팅되어 있어야 한다. 아니면 인증을 시도하지 않는다.
	   props.put("mail.smtp.port", "587");    //네이버는 587. 
	   
	   Authenticator auth = new MyAuthentication();
	   if (debug) props.put("mail.debug", "true");  //현재 디버그 상에 property를 못찾는다고 에러가 나오지만 구글링해본결과, v6.0에서 impl되면서 나는 메세지일 뿐이라고 함
	  
	   Session session = Session.getDefaultInstance(props, auth);
	   session.setDebug(debug);
	   
	   try {
	    //네이버는 한번에 100명에게 보낼수 있다.
	    InternetAddress[] address = new InternetAddress[1];
//	    for(int i=0; i<100; i++){
//	     address[i] = new InternetAddress("siro-9@hanmail.net");
//	    }
	     address[0] = new InternetAddress("siro-9@hanmail.net");
	        
	    InternetAddress fromAddr = new InternetAddress(from);
	    Message msg = new MimeMessage(session);
	    //InternetAddress[] address = {new InternetAddress(to)};
	    
	    //닉네임(보내는사람) 세팅
	   fromAddr.setPersonal("수퍼보이", "EUC-KR");
	    msg.setFrom(fromAddr);
	    msg.setRecipients(Message.RecipientType.TO, address);
	    msg.setSentDate(new Date());
	    
	    //제목
	   msg.setSubject("메일 제목 부분입니다");
	    
	    //내용
	   msgText = "메일내용!: <h1>한글 테스트 중입니다 <a href='http://www.naver.com'>www.naver.com</a></h1> <table bgcolor='ccccc'><tr><td>이미지테스트<img src='http://www.rgagnon.com/images/jht.gif'></img></td></tr></table>";
	    msg.setContent(msgText, "text/html; charset=EUC-KR");
	    
	    //setText(text, charset) //setText는 이미지가 안보내짐. 위의 setContent에 이미지를 보낼수 있다
	   //msg.setText(msgText);
	    
	       //메일 전송부분. 현재 2통씩 보내짐. 갑자기 1통씩 보내진다. 
	       Transport.send(msg);
	       
	   }catch(MessagingException mex) {
	    mex.printStackTrace();
	    Exception ex = mex;
	    
	    do {
	           if (ex instanceof SendFailedException) {
	               SendFailedException sfex = (SendFailedException)ex;
	               Address[] invalid = sfex.getInvalidAddresses();
	            if (invalid != null) {
	                System.out.println("    ** Invalid Addresses");
	       if (invalid != null) {
	               for (int i = 0; i < invalid.length; i++) 
	               System.out.println("         " + invalid[i]);
	       }
	      }
	         Address[] validUnsent = sfex.getValidUnsentAddresses();
	         
	      if (validUnsent != null) {
	          System.out.println("    ** ValidUnsent Addresses");
	       if (validUnsent != null) {
	           for (int i = 0; i < validUnsent.length; i++) 
	               System.out.println("         "+validUnsent[i]);
	           }
	      }
	      Address[] validSent = sfex.getValidSentAddresses();
	      
	      if (validSent != null) {
	          System.out.println("    ** ValidSent Addresses");
	       if (validSent != null) {
	           for (int i = 0; i < validSent.length; i++) 
	               System.out.println("         "+validSent[i]);
	       }
	      }
	           }
	          
	           if (ex instanceof MessagingException){
	            ex = ((MessagingException)ex).getNextException();
	           }else{
	               ex = null;
	           }
	          } while (ex != null);
	   }
	  }
	 }


	 class MyAuthentication extends Authenticator {
	  
	     PasswordAuthentication pa;
	     
	     public MyAuthentication(){
	         //smtp server의 아이디와 패스워드를 입력한다.
	      pa = new PasswordAuthentication("siro9@naver.com","Sangsil3");
	     }

	 // 아래의 메소드는 시스템측에서 사용하는 메소드이다.
	     public PasswordAuthentication getPasswordAuthentication() {
	         return pa;
	     }
	 }



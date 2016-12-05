package com.mee.sangsil.common;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailTest {

	/**
	 * @param args
	 */
	public static void main12(String[] args) {
		// TODO Auto-generated method stub

		 //  SimpleEmail email = new SimpleEmail();
		Email email = new SimpleEmail();
		// setHostName에 실제 메일서버정보

		try {
			email.setCharset("euc-kr"); // 한글 인코딩 
			email.setHostName("smtp.naver.com"); //SMTP서버 설정
			email.setSmtpPort(465);  //포트번호
			email.setAuthentication("siro9", "Sangsil3"); //메일인증  
			email.setSSL(true);   //모르겠음
			email.setTLS(true);
			email.setMsg("This is a test mail ... :-)"); // 메일 제목
			email.setDebug(true);
			email.setContent("simple 메일 naverTest입니다", "text/plain; charset=euc-kr");
			email.setSubject("TestMail");
			email.setFrom("siro9@naver.com", "메일마스타");
			email.addTo("siro-9@daum.net", "받아라"); // 수신자 추가
			email.addTo("xxxxx@naver.com", "sunjin");
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
			System.out.println("에러");
		}
		
		
		
	}

}

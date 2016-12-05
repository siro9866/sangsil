package com.mee.sangsil.sample.jaxbcontext;

import java.io.File;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * JAXBContext 를 이용해 XML 파싱.
 */

@Controller
public class JAXBContextSampleController {

	@RequestMapping(value="/sample/jaxbcontext")
	public ModelAndView jaxbcontext() throws JAXBException {
		// TODO Auto-generated method stub

		JAXBContext jc = JAXBContext.newInstance(AddressBook.class);

		String requestXml = "";
		AddressBook responseAddressBook = new AddressBook();
		
		// Object -> xml
		requestXml = marshall(jc);
		// xml -> Object
		responseAddressBook = unmarshall(jc);

		
		ModelAndView mav = new ModelAndView();
		mav.addObject("requestXml", requestXml);
		mav.addObject("responseAddressBook", responseAddressBook);
		mav.setViewName("/sample/jaxbcontext");
		return mav;
	}

	private  String marshall(JAXBContext jc) throws JAXBException {
		// TODO Auto-generated method stub

		// 어드레스 정보를 생성
		AddressBook adressBook = createAddressBook();

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		// AddressBooks.xml파일저장
		marshaller.marshal(adressBook, new File("./AddressBooks.xml"));
		// 콘솔 화면에 출력
//		marshaller.marshal(adressBook, System.out);
		
		StringWriter sw = new StringWriter();
		marshaller.marshal(adressBook, sw);
		String requestXML = "";
		requestXML = sw.toString();
		System.out.println("requestXML:"+requestXML);
		return requestXML;
	}

	private  AddressBook createAddressBook() {
		// TODO Auto-generated method stub
		AddressBook adressBook = new AddressBook();

		// 유저
		User user = new User();
		user.setName("Mr Kim");
		user.setPhone("12334344");
		user.setAddress("서욽특별시 서초구 양재동");

		// 여러메일 저장
		Emails emails = new Emails();
		// 메일1
		Email email = new Email();
		email.setId("Naver");
		email.setEmailAddr("abc@naver.com");
		emails.getEmail().add(email);

		// 메일2
		email = new Email();
		email.setId("Nate");
		email.setEmailAddr("abc@nate.com");
		emails.getEmail().add(email);

		// 메일3
		email = new Email();
		email.setId("Daum");
		email.setEmailAddr("abc@hanmail.net");
		emails.getEmail().add(email);

		// 유저에 메일정보 설정
		user.setEmails(emails);

		// 어드레스북에 유저 정보 설정
		adressBook.getUser().add(user);

		return adressBook;
	}

	private  AddressBook unmarshall(JAXBContext jc) throws JAXBException {
		// TODO Auto-generated method stub
		Unmarshaller unMarshaller = jc.createUnmarshaller();
		// AddressBooks.xml에서  어드레스 정보를 읽어들임.
		AddressBook adressBooks = (AddressBook) unMarshaller
				.unmarshal(new File("./AddressBooks.xml"));

		// 어드레스 정보에서 유저정보 취득
		List<User> users = (List<User>) adressBooks.getUser();
		for (User user : users) {
			System.out.println("**********************************");
			System.out.println("Name    : " + user.getName());
			System.out.println("Phone   : " + user.getPhone());
			System.out.println("Address : " + user.getAddress());
			
			List<Email> emails = (List<Email>) user.getEmails().getEmail();
			for (Email email : emails) {
				System.out.println("----------------------------");
				System.out.println("id        : "+ email.getId());
				System.out.println("emailAddr : "+ email.getEmailAddr());
			}
			System.out.println("**********************************");
		}
		
		return adressBooks;
		
	}
	
	
	
}

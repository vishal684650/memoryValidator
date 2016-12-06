package com.mindtree;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailClass 
{
	public static void sendMail(String hostName, Map<File, List<Double>> highUsedDrives)
	{
		final String fromEmail = "ptteam.adidas@gmail.com";
		final String password = "Adidas@123";
		final String toEmail = "vishal684650@gmail.com";
		String driveMessage = "";
		String subject = hostName + " Machine is Almost Full";
		String mailBody = "Hi, <br><br><br> "+hostName
				+ " Machine is Almost Full. Below are the statistics for all the drives which are more than 70% used "
				+ "<br><br>"
				+ "<Table border=\"1\"><tr><th>Drive Name</th><th>Total Space</th><th>Free Space</th><th>Used Space</th></tr>";
		
		for(Map.Entry<File,List<Double>> entry : highUsedDrives.entrySet())
		{
			List<Double> spaceDetails = entry.getValue();
			driveMessage = driveMessage+"<tr><td>"+entry.getKey()+"</td><td>"+spaceDetails.get(0)+"GB</td><td>"+spaceDetails.get(1)+"GB</td><td>"+spaceDetails.get(2)+"%</td></tr>";
		}
		
		mailBody = mailBody + driveMessage + "</table> <br><br>Please Cleanup above drives storage on Priority."
				+ "<br><br>Regards<br><br>GTT Performance Team";

		//Get the session object  
		Properties props = new Properties();  
		props.put("mail.smtp.host", "smtp.gmail.com");  
		props.put("mail.smtp.socketFactory.port", "465");  
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		props.put("mail.smtp.auth", "true");  
		props.put("mail.smtp.port", "465");  

		Session session1 = Session.getDefaultInstance(props,  
				new javax.mail.Authenticator() {  
			protected PasswordAuthentication getPasswordAuthentication() {  
				return new PasswordAuthentication(fromEmail,password);//change accordingly  
			}  
		}); 

		//compose message  
		try {  
			MimeMessage message = new MimeMessage(session1);  
			message.setFrom(new InternetAddress(fromEmail));//change accordingly  
			message.addRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));  
			message.setSubject(subject);  
			message.setContent(mailBody, "text/html; charset=ISO-8859-1"); 

			//send message  
			Transport.send(message);  
		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
		}  
	}
}

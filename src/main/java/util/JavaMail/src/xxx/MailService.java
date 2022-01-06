package util.JavaMail.src.xxx;

import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import redis.clients.jedis.Jedis;

public class MailService {
	
	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
	public void sendMail(String to, String subject, String controller, String memAcct) {
			
	   try {
		   // 設定使用SSL連線至 Gmail smtp Server
		   Properties props = new Properties();
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.socketFactory.port", "465");
		   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.port", "465");

		  // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
		  // ●須將myGmail的【安全性較低的應用程式存取權】打開
		  final String myGmail = "okaerihelpcenter@gmail.com";
	      final String myGmail_password = "tibaMe104@";
		   Session session = Session.getInstance(props, new Authenticator() {
			   protected PasswordAuthentication getPasswordAuthentication() {
				   return new PasswordAuthentication(myGmail, myGmail_password);
			   }
		   });
		   
		   //給他一個token放進連結
		   String token = UUID.randomUUID().toString().replace("-", "");
		   StringBuilder messageText = new StringBuilder();
		   messageText.append("<p>請點選以下連結進行修改，提醒您連結將於3分鐘後失效</p>");
		   messageText.append("<a href='http://35.194.162.170:8443/okaeri/"); // need to change localhost once we use GCP
		   messageText.append(controller);
		   messageText.append("?token=");
		   messageText.append(token);
		   messageText.append("'>");
		   messageText.append(" <FONT face='MS UI Gothic' size='3'> <b>");
		   messageText.append(subject);
		   messageText.append("</b></FONT></a>");
		   
		   Message message = new MimeMessage(session);
		   message.setFrom(new InternetAddress(myGmail));
		   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
		  
		   //設定信中的主旨  
		   message.setSubject(subject);
		   //設定信中的內容 
		   message.setContent(messageText.toString(), "text/html; charset=UTF-8");

		   Transport.send(message);
		   Jedis jedis = new Jedis("localhost", 6379);
		   jedis.set(token, memAcct);
		   System.out.println(token);
		   jedis.expire(token, 180); // setting timeout time (jedis will delete this token)
		   System.out.println("傳送成功!");
		   jedis.close();
     }catch (MessagingException e){
	     System.out.println("傳送失敗!");
	     e.printStackTrace();
     }
   }
	
//	 public static void main (String args[]){
//
//      String to = "xuanooo4@gmail.com";
//      String subject = "密碼通知";
//      String ch_name = "David老師";
//      String passRandom = "113355";
//      String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" +" (已經啟用)"; 
//       
//      MailService mailService = new MailService();
//      mailService.sendMail(to, subject, messageText);
//
//   }


}

package com.mywulianwang.www.tool;


import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

@SuppressWarnings("all")
public class SendOrderMailHelper implements Runnable {

	private String serverAddress="smtp.winenice.cn"; // 服务器
	private String user="service9@winenice.cn"; // 用户名
	private String pwd="MroPT3829";// 密码
	private String from="service9@winenice.cn";// 发送人
	private String emailReceiver;// 接收人
	private String subject;// 标题
	private String htmlContents;// 内容
	
	private MimeMessage message;// 创建消息对象
	private Session session;// 邮件会话对象
	private Multipart mp;
	private BodyPart bp;
	private Transport transport;
	private Properties properties;

	/**
	 * 邮件发送 多线程
	 * 参数传值
	 * 
	 * @param serverAddress
	 *            服务器
	 * @param user
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param from
	 *            发送人
	 * @param emailReceiver
	 *            接收人
	 * @param subject
	 *            标题
	 * @param htmlContents
	 *            内容
	 */
	
	public SendOrderMailHelper(String emailReceiver, String subject,
			String htmlContents) {
		this.emailReceiver = emailReceiver;
		this.subject = subject;
		this.htmlContents = htmlContents;

	}

	public void run() {
		properties = new Properties();
		properties.put("mail.smtp.host", serverAddress);
		properties.put("mail.smtp.auth", "true");
		session = Session.getInstance(properties);
		// debug 查看器
		session.setDebug(true);
		// 新建一个消息对象
		message = new MimeMessage(session);
		mp = new MimeMultipart();
		bp = new MimeBodyPart();

		try {
			// 设置发件人
			message.setFrom(new InternetAddress(from));

			// 发送方式
			Transport transport = session.getTransport("smtp");
			// 设置普通文字内容
			// message.setText("普通文字");//普通文字htmlContents

			// 设置html内容
			bp.setContent(htmlContents, "text/html;charset=gbk"); // html内容
			mp.addBodyPart(bp);
				// 设置收件人
				InternetAddress to_mail = new InternetAddress(emailReceiver);
				message.setRecipient(Message.RecipientType.TO, to_mail);
				// 设置主题
				message.setSubject(subject);

				// 设置发送时间
				message.setSentDate(new Date());
				// 发送邮件
				message.setContent(mp);// 内容放入message html
				message.saveChanges(); // 保证报头域同会话内容保持一致

				transport.connect(serverAddress, user, pwd);// 连接到你自己的主机
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				System.out.println("邮件已发至:" + emailReceiver + "!\n");
		} catch (AddressException e) {
			System.out.println("邮件发送地址错误！或空指针错误！不能建立邮件服务器连接！！\n");
			return;
		} catch (MessagingException e) {
			System.out.println("连接邮件主机或服务器错误！！\n");
			return;
		}
	}
	
//	public static void main(String[] args) {
//	SendOrderMailHelper sms = new SendOrderMailHelper("479525769@qq.com", "123456","asdsadsad");
//	new Thread(sms).start();	
//	}

}

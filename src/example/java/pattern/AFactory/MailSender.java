package pattern.AFactory;

public class MailSender implements Sender {

	@Override
	public void send() {
		System.out.println("发送邮件");
	}

}

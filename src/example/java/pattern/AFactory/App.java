package pattern.AFactory;

public class App {

	public static void main(String[] args) {
		Provider provider = new MailSenderFactory();
		Sender  sender = provider.product();
		sender.send();
	}

}

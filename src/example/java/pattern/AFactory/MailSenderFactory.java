package pattern.AFactory;

public class MailSenderFactory implements Provider {

	@Override
	public Sender product() {
		return new MailSender();
	}

}

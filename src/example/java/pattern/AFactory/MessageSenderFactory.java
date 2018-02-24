package pattern.AFactory;

public class MessageSenderFactory implements Provider {

	@Override
	public Sender product() {
		return new MessageSender();
	}

}

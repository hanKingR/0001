package pattern.AFactory;

public class MessageSender implements Sender {

	@Override
	public void send() {
		System.out.println("发送信息");
	}

}

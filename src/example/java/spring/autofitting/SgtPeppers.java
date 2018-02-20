package spring.autofitting;

import org.springframework.stereotype.Component;

//播放机
@Component("sgtPeppers")
public class SgtPeppers implements CompactDisc {
	private String title ="sgt Pepper's Lonely Hearts Club Band";
	private String artist="The Beatles"; 
	
	public void play() {
		System.out.println("Playing "+title+"by"+artist);
	}

}

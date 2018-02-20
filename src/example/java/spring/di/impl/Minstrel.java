package spring.di.impl;

import java.io.PrintStream;
//吟游诗人 类似于AOP
public class Minstrel {
	private  PrintStream stream;
	
	public Minstrel(PrintStream stream) {
		this.stream =stream ;
	}
	//探险之前用
	public void singBeforeQuest() {
		stream.println("fa la la , the knight is so brave!");
	}
	//探险之后用
	public void singAfterQuest() {
		stream.println("tee hee hee , the brave knight "+"did emnark on aquest!");
	}
}

package spring.di.impl;

import java.io.PrintStream;

import spring.di.Quest;

//任务实现类
public class SlayDragonQuest implements Quest {
	private PrintStream stream;

	public SlayDragonQuest(PrintStream stream) {
		this.stream = stream;
	}

	public void embark() {
		stream.println("Embarking on quest to slay the dragon");
	}
}

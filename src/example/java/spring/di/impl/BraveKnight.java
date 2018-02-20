package spring.di.impl;

import spring.di.Knight;
import spring.di.Quest;

public class BraveKnight implements Knight {
	
	private Quest quest;
	
	public BraveKnight (Quest quest) {
		this.quest = quest;
	}
	
	public void embarkOnQuest() {
		quest.embark();
	}

}

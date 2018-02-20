package spring.di.mock;


import org.junit.Test;
import org.mockito.Mockito;

import spring.di.Quest;
import spring.di.impl.BraveKnight;

//测试类
public class BraveKnightTest {
	
	@Test
	public void knightShouldEmbarkOnQuest() {
		//创建mock Quest;
		Quest mockQuest = Mockito.mock(Quest.class);
        BraveKnight knight = new BraveKnight(mockQuest);
        knight.embarkOnQuest();
        Mockito.verify(mockQuest,Mockito.times(1)).embark();
	}
}

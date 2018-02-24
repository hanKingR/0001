package spring;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class AAA {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Resource resource;
//		resource =  new FileSystemResource("aaa.xml");
		resource = new ClassPathResource("aaa.xml");
//		resource=new PathResource("aaa.xml");
		XmlBeanFactory aa = new XmlBeanFactory(resource);
		ABC bb = (ABC)aa.getBean("knight");
//		bb.aaa();
		
	}

}

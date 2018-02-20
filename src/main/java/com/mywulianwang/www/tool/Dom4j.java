package com.mywulianwang.www.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sun.beans.decoder.DocumentHandler;

@SuppressWarnings("unchecked")
public class Dom4j {

	static Logger log = Logger.getLogger("PLATFORM");

	public Dom4j() {
	}

	public Document getDocument(String fileName) throws Exception {
		try {
			SAXReader reader = new SAXReader();
			Document document = reader.read(new File(fileName));
			return document;
		} catch (DocumentException e) {
			log.error("Dom4j.getDocument DocumentException:" + e);
			throw new Exception("Dom4j.getDocument DocumentException:" + e);
		}
	}
	//得到根节点
	public Element getRootElement(Document doc) {
		return doc.getRootElement();
	}
    //得到根节点以下的所有元素
	public ArrayList<Element> getElement(Element root) {
		ArrayList<Element> elementAl = new ArrayList<Element>();
		Element element;
		for (Iterator i = root.elementIterator(); i.hasNext(); elementAl.add(element))
			{element = (Element) i.next();}

		return elementAl;
	}
    //查询绝对路径
	public void query(Document doc, String xPath) {
		List list = doc.selectNodes(xPath);
		for (Iterator iter = list.iterator(); iter.hasNext(); System.out.println(iter.next()))
			;
	}
	
	public static void main(String[] args) {
		String str = "D:/struts.xml";
		Dom4j dom4j = new Dom4j();
		try{
			//得到Document 对象
			Document document = dom4j.getDocument(str);
			//获取根节点
			Element el = dom4j.getRootElement(document);
			System.out.println(el.getName());
			List<Element> list = dom4j.getElement(el);
			//循环根节点下的子节点
			for(Iterator<Element> x=list.iterator();x.hasNext();){
				Element element = x.next();
				System.out.println("********************获取信息************************");
				System.out.println("getName            ==>>"+element.getName());
				//得到当前节点中的属性
				List<Attribute> list1 = element.attributes();  
				for(Iterator<Attribute> y=list1.iterator();y.hasNext();){
					Attribute attribute = y.next();
					System.out.println("*****************"+attribute.getName());
					System.out.println("*****************"+attribute.getValue());
				}
				 
//				System.out.println("********************************************");
//				System.out.println("getData            ==>>"+element.getData());
//				System.out.println("getStringValue     ==>>"+element.getStringValue());
//				System.out.println("getText            ==>>"+element.getText());
//				System.out.println("getTextTrim        ==>>"+element.getTextTrim());
//				System.out.println("********************************************");
//				System.out.println("getNamespacePrefix ==>>"+element.getNamespacePrefix());
//				System.out.println("getNamespaceURI    ==>>"+element.getNamespaceURI());
//				System.out.println("********************************************");
//				System.out.println("getNodeType        ==>>"+element.getNodeType());
//				System.out.println("getNodeTypeName    ==>>"+element.getNodeTypeName());
//				System.out.println("getParent          ==>>"+element.getParent());
//				System.out.println("********************************************");
//				System.out.println("getPath            ==>>"+element.getPath());
//				System.out.println("getUniquePath      ==>>"+element.getUniquePath());
//				System.out.println("********************************************");
//				System.out.println("getDocument        ==>>"+element.getDocument());
//				System.out.println("********************************************");
//				System.out.println("getNamespace       ==>>"+element.getNamespace());
//				System.out.println("********************************************");
				//创建XML
						
			}
			
		}catch(DocumentException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}

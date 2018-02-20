package com.mywulianwang.www.tool;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class SMS {
	
	public static void sendMt2(String smsContent, String mobile) {
		try {
			String fromUserName = "winenice_com";
			String keyPWD = "79003816wN";
			String SendTime = "";
			int smsPriority = 5;

			beartool.MD5 md5 = new beartool.MD5();
			String md5Key = new String(md5.getMD5ofStr(
					(fromUserName + keyPWD + mobile)).getBytes(), "UTF-8");
			String PWD = md5Key;

			System.out.print(md5Key);

			// webserviceURL
			String service_url = "http://edm.4006008009.cn/SendSms.asmx?wsdl";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(service_url));

			call
					.setOperationName(new QName("http://tempuri.org/",
							"sendSms_YW"));

			call.addParameter(new QName("http://tempuri.org/", "Mobile"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "Content"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "SendTime"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter("smsPriority",
					org.apache.axis.encoding.XMLType.XSD_INT,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "FromUserName"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "PWD"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setSOAPActionURI("http://tempuri.org/sendSms_YW");

			String xmlStr = call.invoke(
					new Object[] { mobile, smsContent, SendTime, smsPriority,
							fromUserName, PWD }).toString();
			System.out.println("xmlStr=====发送完成=============" + xmlStr);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("===========发送出错==========");
		}
	}		

	/**
	 * 业务短信
		webservice地址：http://wcf.winenice.com/SendAloneSMS/SendAloneSMS.asmx 
		1调用短信接口
		sAloneSMS(string mobile, string content, string memo, string Verification) 
		mobile：手机号
		content：内容
		memo：短信类型，例如订单短信，出货短信等
		Verification：字符串验证 strMD5（mobile）
		2加密接口
		strMD5(string str) 

	 * @param smsContent
	 * @param mobile
	 */
	public static void sendMt(String smsContent, String mobile) {
		try {
			String fromUserName = "winenice_com";
			String keyPWD = "79003816wN";
			String SendTime = "";
			int smsPriority = 5;

			beartool.MD5 md5 = new beartool.MD5();
			String md5Key = new String(md5.getMD5ofStr(mobile).getBytes(), "UTF-8");
			String PWD = md5Key;

			System.out.print(md5Key);

			// webserviceURL
			String service_url = "http://wcf.winenice.com/SendAloneSMS/SendAloneSMS.asmx?wsdl";
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(service_url));

			call
					.setOperationName(new QName("http://tempuri.org/",
							"sAloneSMS"));

			call.addParameter(new QName("http://tempuri.org/", "Mobile"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "Content"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "memo"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);
			call.addParameter(new QName("http://tempuri.org/", "Verification"),
					org.apache.axis.encoding.XMLType.XSD_STRING,
					javax.xml.rpc.ParameterMode.IN);

			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

			call.setSOAPActionURI("http://tempuri.org/sAloneSMS");

			String xmlStr = call.invoke(
					new Object[] { mobile, smsContent, "出货短信", md5Key}).toString();
			System.out.println("xmlStr=====发送完成=============" + xmlStr);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("===========发送出错==========");
		}
	}
	
	
}

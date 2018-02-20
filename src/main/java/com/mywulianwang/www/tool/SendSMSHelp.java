package com.mywulianwang.www.tool;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

/**
 * 短信发送
 * 
 * @param String
 *            mobile //收信人
 * @param String
 *            smsContent // 收信内容
 */

public class SendSMSHelp implements Runnable{
	private String mobile;
	private String smsContent;

	public SendSMSHelp(String mobile, String smsContent) {
		this.mobile = mobile;
		this.smsContent = smsContent;

	}

	public void run() {

		// String fromUserName =
		// SystemManager.getValueByKey("order.sms.userid");
		// String keyPWD = SystemManager.getValueByKey("order.sms.password");

		// SimpleDateFormat sdf = new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss "
		// );
		// String SendTime = sdf.format( new Date());
		
		String SendTime = "";
		int smsPriority = 5;
		String fromUserName = "winenice_com";
		String keyPWD = "79003816wN";

		beartool.MD5 md5 = new beartool.MD5();
		String md5Key = null;
		try {
			md5Key = new String(md5.getMD5ofStr(
					(fromUserName + keyPWD + mobile)).getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String PWD = md5Key;

		// System.out.println(md5Key);
		// 核心代码
		String Service_url = "http://edm.4006008009.cn/SendSms.asmx?wsdl";
		Service service = new Service();
		try {
			Call call = (Call) service.createCall();
			// 设置URL
			call.setTargetEndpointAddress(new java.net.URL(Service_url));

			// 设置调用方法
			call
					.setOperationName(new QName("http://tempuri.org/",
							"sendSms_YW"));
			// 添加该方法需要的参数
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
			// 设置返回值
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
			// action uri
			// call.setUseSOAPAction(true);
			call.setSOAPActionURI("http://tempuri.org/sendSms_YW");
			// 调用该方法, new Object[] { CustNo, passwd, Jobno}为参数列表
			String xmlStr = call.invoke(
					new Object[] { mobile, smsContent, SendTime, smsPriority,
							fromUserName, PWD }).toString();
			System.out.println("=============发送成功==================" + xmlStr);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

//	public static void main(String[] args) {
//	SendSMSHelp sms = new SendSMSHelp("15910732110", "123456");
//	new Thread(sms).start();	
//	}

}

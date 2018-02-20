package com.mywulianwang.www.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.hibernate.sql.Delete;

import com.winenice.web.util.other.CookiePsdCryptUtil;
import com.winenice.web.domain.back.account.Order;
import com.winenice.web.domain.back.user.User;
import com.winenice.web.util.other.AgencyUtil;


@SuppressWarnings("all")
public class CookieUtils {
	public final static String COOKIE_NAME = "winenice.com";
	
	public final static String COOKIE_CM_ID="cookiecmid";
	public final static String WINE_CUSTOMER_NAME = "username";
	public final static String WINE_REMEMBER_NAME = "remembername";
	public final static String WINE_NICk_NAME = "nickname";
	public final static int USERNAME_MAXAGE = 24*60*60;
	public final static int USERNAME_DEFAGE = -1;
	public final static String COOKIE_USERSTORDCARD = "useStoredCard";
	public final static String COOKIE_USEUSERGIFTCARD  ="useUserGiftCard";
	public final static String COOKIE_USERGIFTNO  ="user_gift_no";
	public final static String COOKIE_USERGIFTPASSWORD  ="user_gift_password";
	public final static String COOKIE_USEUSERGIFTCARDPRICE = "useUserGiftCardPirce";
	public final static String COOKIE_USEGIFTCARD ="useGiftCard";
	public final static String COOKIE_GIFTNO = "gift_no";
	public final static String COOKIE_GIFTPASSWORD = "gift_password";
	public final static String COOKIE_GIFTPRICE ="gift_price";
	
	public final static String COOKIE_TH ="th";
	public final static String COOKIE_TH_GIFT_NO ="gift_no_th";
	public final static String COOKIE_TH_GIFT_PASSWORD ="gift_password_th";
	/**
	 * 把商品加入购物车
	 * @param request
	 * @param response
	 * @param product_no	商品编号
	 * @param quantity	商品数量
	 */
	public static void setShoppingCart(HttpServletRequest request,HttpServletResponse response, String product_no, int quantity) {
		if (product_no != null && !product_no.equals("") && quantity > 0) {
			String str = String.valueOf(quantity);
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("wine" + product_no.trim())) {
						cookies[i].setValue(str);
						cookies[i].setMaxAge(999999);
						cookies[i].setPath("/");
						//cookies[i].setDomain(COOKIE_NAME);
						response.addCookie(cookies[i]);
						break;
					}
					if (i == (cookies.length - 1)) {
						Cookie newCookie = new Cookie("wine" + product_no.trim(),str);
						newCookie.setMaxAge(999999);
						newCookie.setPath("/");
						//newCookie.setDomain(COOKIE_NAME);
						response.addCookie(newCookie);
					}
				}

			}else{
				Cookie newCookie = new Cookie("wine" + product_no.trim(),str);
				newCookie.setMaxAge(999999);
				newCookie.setPath("/");
				//newCookie.setDomain(COOKIE_NAME);
				response.addCookie(newCookie);
			}
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param product_no	商品编号
	 */
	public static int getShoppingCartProductNum(HttpServletRequest request,HttpServletResponse response, String product_no) {
		int productNum = 0;
		if (product_no != null && !product_no.equals("")) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("wine" + product_no.trim())) {
						if(cookies[i].getValue() != null){
							productNum = Integer.parseInt(cookies[i].getValue());
							return productNum;
						}
						break;
					}
				}
			}
		}
		return productNum;
	}
	
	/**
	 * 修改购物车商品数量
	 * @param request
	 * @param response
	 * @param product_no	商品编号
	 * @param quantity	数量
	 */
	public static void updateShoppingCart(HttpServletRequest request,HttpServletResponse response, String product_no, int quantity) {
		if (product_no != null && !product_no.equals("") && quantity > 0) {
			String str = String.valueOf(quantity);
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("wine" + product_no.trim())) {
						cookies[i].setValue(str);
						cookies[i].setMaxAge(999999);
						cookies[i].setPath("/");
						//cookies[i].setDomain(COOKIE_NAME);
						response.addCookie(cookies[i]);
						break;
					}
					if (i == (cookies.length - 1)) {
						Cookie newCookie = new Cookie("wine" + product_no.trim(),str);
						newCookie.setMaxAge(999999);
						newCookie.setPath("/");
						//newCookie.setDomain(COOKIE_NAME);
						response.addCookie(newCookie);
					}
				}

			}
		}
	}
	
	/**
	 * 根据商品编号删除
	 * @param request
	 * @param response
	 * @param product_no	商品编号
	 */
	public static void dropShoppingCartByNo(HttpServletRequest request,HttpServletResponse response, String product_no) {
		if (product_no != null && !product_no.equals("")) {
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().length()>4){
					if (cookies[i].getName().equals("wine" + product_no.trim())) {
						cookies[i].setMaxAge(0);
						cookies[i].setPath("/");
						//cookies[i].setDomain(COOKIE_NAME);
						response.addCookie(cookies[i]);
						break;
					}
					}
				}
			}
		}
	}
	
	/**
	 * 获取购物车商品数量
	 * @param request
	 * @param response
	 * @return
	 */
	public static int getShoppingCartCount(HttpServletRequest request,HttpServletResponse response) {
		int count  = 0;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().length()>4){
				if (cookies[i].getName().substring(0, 4).equals("wine")) {
					count += 1;
				}
				}
			}
		}
		return count;
	}
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 */
	public static void dropShoppingCart(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().length()>4){
				if (cookies[i].getName().substring(0, 4).equals("wine")) {
					cookies[i].setMaxAge(0);
					cookies[i].setPath("/");
					//cookies[i].setDomain(COOKIE_NAME);
					response.addCookie(cookies[i]);
				}
				}
			}
		}
	}
	
	/**
	 * 获取购物车列表
	 * @param request
	 * @param response
	 * @return	Map<product_no,quantity>
	 */
	public static Map<String,String> getShoppingCart(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> map = new HashMap<String,String>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if(cookies[i].getName().length()>4){
				if (cookies[i].getName().substring(0, 4).equals("wine")) {
					map.put(cookies[i].getName().substring(4), cookies[i].getValue());
				}
				}
			}
		}else{
			return null;
		}
		return map;
	}
	/**
	 * 登陆注册后 Cookie存入
	 * 
	 * @param request
	 * @param response
	 * @param customername
	 */
	public static void saveUser(HttpServletRequest request,HttpServletResponse response,String customername,String psd){
		Cookie usernameCookie = new Cookie(WINE_CUSTOMER_NAME, CookiePsdCryptUtil.encryptAES(customername+":"+psd));
		usernameCookie.setMaxAge(USERNAME_DEFAGE);
		usernameCookie.setPath("/");
		//usernameCookie.setDomain(COOKIE_NAME);
		response.addCookie(usernameCookie);
	}
	
	public static void saveUser(HttpServletRequest request,HttpServletResponse response,String customername,String psd, Integer autoLogin,String niceName) throws UnsupportedEncodingException{
		
		Cookie usernameCookie = new Cookie(WINE_CUSTOMER_NAME, CookiePsdCryptUtil.encryptAES(customername+":"+psd));
		if(null == niceName){
			niceName = "";
		}
		Cookie nickNameCookie = new Cookie(WINE_NICk_NAME,URLEncoder.encode(niceName,"utf-8") );
		if(autoLogin == 0){
			usernameCookie.setMaxAge(-1);
			nickNameCookie.setMaxAge(-1);
		}else{
			usernameCookie.setMaxAge(999999);
			nickNameCookie.setMaxAge(999999);
		}
		usernameCookie.setPath("/");
		nickNameCookie.setPath("/");
		//usernameCookie.setDomain(COOKIE_NAME);
		response.addCookie(usernameCookie);
		response.addCookie(nickNameCookie);
	}
	
	public static void saveRememberName(HttpServletResponse response,String customername){
		Cookie usernameCookie = new Cookie(WINE_REMEMBER_NAME, CookiePsdCryptUtil.encryptAES(customername));
		usernameCookie.setMaxAge(999999);
		usernameCookie.setPath("/");
		//usernameCookie.setDomain(COOKIE_NAME);
		response.addCookie(usernameCookie);
	}
	
	public static void saveLoginNum(HttpServletRequest request,HttpServletResponse response,String num){
		Cookie loginNumCookie = new Cookie("error_LoginNum",String.valueOf(Integer.valueOf(num)+1));
		loginNumCookie.setMaxAge(1*60*60);
		loginNumCookie.setPath("/");
		response.addCookie(loginNumCookie);
	}
	
	public static String getLoginNum(HttpServletRequest request,HttpServletResponse response){
		String num="1";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie : cookies){
			if("error_LoginNum".equals(cookie.getName())){
				String value = cookie.getValue();
				if(!StringUtil.isEmpty(value)){
					num=value;
				}
			}
			}
		}
		return num;
	}
	
	public static void clear(HttpServletRequest request,HttpServletResponse response){
		Cookie usernameCookie = new Cookie(WINE_CUSTOMER_NAME, "");
		usernameCookie.setMaxAge(USERNAME_DEFAGE);
		usernameCookie.setPath("/");
		//usernameCookie.setDomain(COOKIE_NAME);
		response.addCookie(usernameCookie);
		
		Cookie nickNameCookie = new Cookie(WINE_NICk_NAME, "");
		nickNameCookie.setMaxAge(USERNAME_DEFAGE);
		nickNameCookie.setPath("/");
		//usernameCookie.setDomain(COOKIE_NAME);
		response.addCookie(nickNameCookie);
	}
	
	
	public static String getUsername(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
		for(Cookie cookie : cookies){
			if(WINE_CUSTOMER_NAME.equals(cookie.getName())){
				String value = cookie.getValue();
				if(StringUtil.isEmpty(value)){
					value=null;
				}else{
					value = CookiePsdCryptUtil.decrypt(value);
				}
				return value;
			}
		}
		}
		return null;
	}
	
	public static String getRememberName(HttpServletRequest request,HttpServletResponse response){
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
		for(Cookie cookie : cookies){
			if(WINE_REMEMBER_NAME.equals(cookie.getName())){
				String value = cookie.getValue();
				if(StringUtil.isEmpty(value)){
					value=null;
				}else{
					value = CookiePsdCryptUtil.decrypt(value);
				}
				return value;
			}
		}
		}
		return null;
	}
	
	public static void saveAgencyCookie(HttpServletRequest request,HttpServletResponse response,String from){
		Cookie[] wmcookies = request.getCookies();
		if(wmcookies != null && wmcookies.length>0){
			for (int i = 0; i < wmcookies.length; i++) {
				if (wmcookies[i].getName().equals("FROMCODE")) {
				
					Cookie wmcookie = new Cookie("FROMCODE",""); 
					//wmcookie.setDomain(COOKIE_NAME);	
					wmcookie.setMaxAge(0); 
					wmcookie.setPath("/");
					response.addCookie(wmcookie);
					break;
				}
			}
		}
		
		if(!StringUtil.isEmpty(from)){
			response.setHeader("P3P", "CP=\"NOI DEVa TAIa OUR BUS UNI\"");	
			Cookie newcookie = new Cookie("FROMCODE", from);
			newcookie.setPath("/");
			//newcookie.setDomain(COOKIE_NAME);		
			Integer RD = 7;				//这里是设置COOKIE的有效周期，请按合同规定的广告效果认定期设置， 此处假设广告效果认定期为30天
			if(RD!=0)newcookie.setMaxAge(60*60*24*RD);
			response.addCookie(newcookie);
		}
	}

	public static void saveViewHistory(String productNo,String name,String pic,String scprice,String hyprice){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String product_info_history = getCookieByName(request, response, "product_info");
		StringBuffer productInfo = new StringBuffer();
		try {
			if(product_info_history.indexOf(productNo) < 0){
				String product = productNo+","+URLEncoder.encode(name,"UTF-8")+","+pic+","+scprice+","+hyprice;
				productInfo.append(product);
				
				if(!StringUtil.isBlank(product_info_history) && product_info_history.split(":").length >= 5){
					product_info_history = product_info_history.substring(0, product_info_history.lastIndexOf(":")-1);
				}
				if(!StringUtil.isBlank(product_info_history)){
					productInfo.append(":").append(product_info_history);
				}
				delteCookieByName(request, response, "product_info");
				Cookie newCookie = new Cookie("product_info",String.valueOf(productInfo));
				newCookie.setMaxAge(999999);
				newCookie.setPath("/");
				response.addCookie(newCookie);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通用获取cookie方法
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request,HttpServletResponse response, String name){
		String cookieString = "";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie : cookies){
			if(name.equals(cookie.getName())){
				String value = cookie.getValue();
				if(!StringUtil.isEmpty(value)){
					cookieString = value;
				}
			}
			}
		}
		return cookieString;
	}
	
	/**
	 * 通用删除cookie方法
	 * @param request
	 * @param response
	 * @param name
	 * @return
	 */
	public static void delteCookieByName(HttpServletRequest request,HttpServletResponse response, String name){
		String cookieString = "";
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
			for(Cookie cookie : cookies){
				if(name.equals(cookie.getName())){
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}
	
	
	public static void saveCookie(HttpServletResponse response,String key,String value){
		Cookie c = new Cookie(key,value);
		c.setMaxAge(-1);
		c.setPath("/");
		response.addCookie(c);
	}
	
	public static void saveCookie(HttpServletResponse response,String key,String value,int age){
		Cookie c = new Cookie(key,value);
		c.setMaxAge(age);
		c.setPath("/");
		response.addCookie(c);
	}
	
	/*public static void saveCookiecmid(HttpServletResponse response,String cookiecmid){
		Cookie c = new Cookie(COOKIE_CM_ID,cookiecmid);
		c.setMaxAge(30*24*60*60);
		c.setPath("/");
		response.addCookie(c);
	}
	//COOKIE_USERSTORDCARD
	
	public static void saveUseStoredCard(HttpServletResponse response,String str){
		Cookie c = new Cookie(COOKIE_USERSTORDCARD,str);
		c.setMaxAge(30*24*60*60);
		c.setPath("/");
		response.addCookie(c);
	}
	//COOKIE_USEUSERGIFTCARD
	public static void saveUseUserGiftCard(HttpServletResponse response,String str){
		Cookie c = new Cookie(COOKIE_USEUSERGIFTCARD,str);
		c.setMaxAge(30*24*60*60);
		c.setPath("/");
		response.addCookie(c);
	}*/
}

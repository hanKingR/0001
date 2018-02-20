package com.mywulianwang.www.tool;

 import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;
   
 /** 
  * @author Zhiwei Wang
  *  
  */
@SuppressWarnings("unchecked")
 public class DateTypeConverter extends StrutsTypeConverter {  

	 private static final Logger logger = Logger.getLogger(DateTypeConverter.class);  
     public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";  
     
     //暂时只考虑这几种日期格式  
     public static final DateFormat[] ACCEPT_DATE_FORMATS = {  
             new SimpleDateFormat(DEFAULT_DATE_FORMAT),  
             new SimpleDateFormat("yyyy年MM月dd日"),  
             new SimpleDateFormat("yyyy/MM/dd") };  
   
     /** 
      *  默认构造函数
      */  
     public DateTypeConverter() {  
    	 
     }
     
	 @Override  
     public Object convertFromString(Map context, String[] values, Class toClass) {  
    	 Date date = null;
         String dateString = null;      
         if (values != null && values.length > 0) {      
             dateString = values[0];      
             if (dateString != null) {      
                 SimpleDateFormat format = new SimpleDateFormat(DEFAULT_DATE_FORMAT);      
                 try {
                     date = format.parse(dateString);
                 } catch (ParseException e) {
                     date = null;
                 }
             }
         }
         return date;
     }
   
     @Override
     public String convertToString(Map context, Object o) {  
         if (o instanceof Date) {  
             SimpleDateFormat format = new SimpleDateFormat(  
                     DEFAULT_DATE_FORMAT);  
             try {  
                 return format.format((Date) o);  
             } catch (RuntimeException e) {
            	 logger.info("Date convert string error!");
                 return "";
             }
         }
         return "";
     }
}
package com.mywulianwang.www.tool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;


@SuppressWarnings("all")
public class DateUtil {
    private static String defaultDatePattern = "yyyy-MM-dd";
    private static String pattern = "yyyy-MM-dd HH:mm:ss";
    
    public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond
    
    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return defaultDatePattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }
    
    public static String getToday(String pattern) {
        Date today = new Date();
        return format(today,pattern);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return date == null ? "" : format(date, getDatePattern());
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String formatHms(Date date) {
        return date == null ? "" : format(date, getPattern());
    }
    
    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? "" : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : parse(strDate, getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 在日期上增加数个整月
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }
    
    public static Date addDay(Date date,int n){
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, n);
        return cal.getTime();
    }
    public static String changeDatetoStringYear(Date date) {
		if (date != null) {
			return (new SimpleDateFormat("yyyy")).format(date);
		} else {
			return "";
		}
	}
    public static String changeDatetoStringMonth(Date date) {
		if (date != null) {
			return (new SimpleDateFormat("MM")).format(date);
		} else {
			return "";
		}
	}
    public static String changeDatetoStringDay(Date date) {
		if (date != null) {
			return (new SimpleDateFormat("dd")).format(date);
		} else {
			return "";
		}
	}

	public static String getDefaultDatePattern() {
		return defaultDatePattern;
	}

	public static void setDefaultDatePattern(String defaultDatePattern) {
		DateUtil.defaultDatePattern = defaultDatePattern;
	}

	public static String getPattern() {
		return pattern;
	}

	public static void setPattern(String pattern) {
		DateUtil.pattern = pattern;
	}
	
	public static Date strToDate(String date) {
		String format="yyyy-MM-dd";
		
		if (date == null)
			return null;
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		try {
			return (Date) formatter.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("wwww:" + e);
		}
	}
	

	public static String getDate() {
		String dateTime = null;
		try {
			DateUtil.setDefaultDatePattern(DateUtil.getPattern());
			dateTime = DateUtil.getToday();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateTime;
	}
	
	public static String getStartDate(){
		//30天内时间段
		return DateUtil.format(DateUtil.addDay(new Date(), -30), DateUtil.getPattern());
		
	}
	public static String getDateLongTime(){
		
		long date = new Date().getTime();
		return String.valueOf(date);
	}
	
	

	/**
	 * 利用指定SimpleDateFormat instance转化String到Date
	 * 
	 * @param sDate
	 *            Date string
	 * @param formatter
	 *            SimpleDateFormat instance
	 * @return
	 * @since 1.0
	 * @history
	 */
	private static java.util.Date toDate(String sDate,SimpleDateFormat formatter) {
		java.util.Date dt = null;
		try {
			dt = formatter.parse(sDate);
		} catch (Exception e) {
			e.printStackTrace();
			dt = null;
		}
		return dt;
	}
	
	/**
	 * Global SimpleDateFormat object
	 */
	public static SimpleDateFormat sdfDateOnly = new SimpleDateFormat(DateUtil.defaultDatePattern);
	
	/**
	 * 以YYYY/MM/DD HH24:MI:SS格式返回系统日期时间
	 * 
	 * @return 系统日期时间
	 * @since 1.0
	 * @history
	 */
/*	public static String getSysDateTimeString() {
		return toString(new java.util.Date(System.currentTimeMillis()),DateUtil.sdfDateTime);
	}*/
	
	public static SimpleDateFormat sdfDateTime = new SimpleDateFormat(
			DateUtil.defaultDatePattern);
	
	//*********************************老版Start**********************
	/**
	 * 根据时间戳字符串获取Tiestamp对象<br/>
	 * 
	 * 输入：2002-02-02 或者 2002-02-02 00:00:00
	 * 
	 * @param timestamp
	 *            时间格式或者日期格式
	 * @return Timestamp对象
	 */
	public static Timestamp parseTimestamp(String timestamp) {
		try {
			return Timestamp.valueOf(timestamp);
		} catch (Exception e) {
			try {
				return Timestamp.valueOf(timestamp.trim() + " 00:00:00");
			} catch (Exception ee) {
				try {
					return Timestamp.valueOf(timestamp.trim() + ":00");
				} catch (Exception eee) {
					try {
						return Timestamp.valueOf(timestamp.trim() + ":00:00");
					} catch (Exception eeee) {
					}
				}
			}
		}

		return null;
	}
	
	/**
	 * 获取Tiestamp<br/>
	 * 
	 * 输入：2002-02-02 或者 2002-02-02 00:00:00
	 * 
	 * @param timestamp
	 *            时间格式或者日期格式
	 */
	public static Timestamp parseTimestamp(String timestamp, boolean bStart) {
		if (bStart) {
			return parseTimestamp(timestamp);
		}

		try {
			return Timestamp.valueOf(timestamp);
		} catch (Exception e) {
			try {
				return Timestamp.valueOf(timestamp.trim() + " 23:59:59");
			} catch (Exception ee) {
			}
		}

		return null;
	}
	/**
	 * 接收形如2000-02-01 01:02:03的时间两个参数转化为long型值后进行比较
	 * 
	 * @param smallTamp
	 * @param bigTamp
	 * @return
	 */
	public static long compareTimestamp(String smallTamp, String bigTamp) {
		if (!StringUtil.isEmpty(smallTamp) && !StringUtil.isEmpty(bigTamp)) {
			try {
				Timestamp t = parseTimestamp(smallTamp, true);
				Timestamp t2 = parseTimestamp(bigTamp, true);
				return t2.getTime() - t.getTime();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			return 0;
		}
		return 0;

	}
	
	/**
	 * 在java.util.DateObject上增加/减少几天
	 * 
	 * @param timestamp
	 *            java.util.Date instance
	 * @param days
	 *            增加/减少的天数
	 * @return java.util.Date Object
	 * @since 1.0
	 * @history
	 */
	public static java.util.Date addDays(java.util.Date date, int days) {
		long temp = date.getTime();

		return new java.util.Date(temp + DateUtil.DAY_MILLI * days);
	}
	
	//*********************************老版End**********************
	/**
	 * 获取本周星期一的日期
	 * 
	 * */
	public static String findMondayByDate(String date){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date dt2 = null;
		String monday = "";
		try {
			dt2 = sdf.parse(date);
			String str = getWeekOfDate(dt2);
			if(str.equals("星期日")){
				String systemDate = addDay(date, -1);
				monday = getMonday(systemDate);
			}else{
				monday = getMonday(date);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return monday;
	}
	//获取周一的日期
	public static String getMonday(String date) {
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 Date d = null;
		 try {
			 d = format.parse(date);
		 } catch(Exception e) {
			 e.printStackTrace();
		 }
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(d);
		 //关于DAY_OF_WEEK的说明
		 //Field number for get and set indicating 
		 //the day of the week. This field takes values 
		 //SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, 
		 //and SATURDAY
		 cal.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		 return format.format(cal.getTime());
	 }

	//获取当前日期是星期几
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0; 
        return weekDays[w];
    }
	/**
	 * 增加n天日期
	 * 
	 * */
	public static String addDay(String s, int n) {    
        try {    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
   
            Calendar cd = Calendar.getInstance();    
            cd.setTime(sdf.parse(s));    
            cd.add(Calendar.DATE, n);//增加n天    
            //cd.add(Calendar.MONTH, n);//增加n个月    
   
            return sdf.format(cd.getTime());    
   
        } catch (Exception e) {    
            return null;    
        }    
   
    }
	/**
	 * 获取下一周的日期  
	 * 
	 * @param currentDate
	 * @return 
	 */
	public static Date nextWeek(Date currentDate) { 
		GregorianCalendar cal = new GregorianCalendar();  
		try{
			cal.setTime(currentDate);  
			cal.add(GregorianCalendar.DATE, 7);//在日期上加7天  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal.getTime();  
	}  
	  
   /**
    * 获取本周日的日期 
    * 
    * @param monday
    * @return
    */
	public static Date getSunday(Date monday) {  
		GregorianCalendar cal = new GregorianCalendar();  
		try{
			cal.setTime(monday);  
			cal.add(GregorianCalendar.DATE, 6);//在日期上加6天  
		}catch(Exception e){
			e.printStackTrace();
		}
	return cal.getTime();  
	}  
	
	/**
	 * 获取15天以后的日期  
	 * 
	 * @param currentDate
	 * @return 
	 */
	public static Date nextFifteen(Date currentDate) { 
		GregorianCalendar cal = new GregorianCalendar();  
		try{
			cal.setTime(currentDate);  
			cal.add(GregorianCalendar.DATE, 15);//在日期上加15天  
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal.getTime();  
	}  
	
	/**
	 * 获取下一月的日期  
	 * 	   
	 * @param currentDate
	 * @return
	 */
	  
	public static Date nextMonth(Date currentDate) {  
		GregorianCalendar cal = new GregorianCalendar();  
		try{
			cal.setTime(currentDate);  
			cal.add(GregorianCalendar.MONTH, 1);//在月份上加1  
		}catch(Exception e){
			e.printStackTrace();
		}
	return cal.getTime();  
	}  

	/**
	 * 获取下一年的日期  
	 * 
	 * @param currentDate
	 * @return
	 */
	public static Date nextYear(Date currentDate) {  
		GregorianCalendar cal = new GregorianCalendar();  
		try{
		cal.setTime(currentDate);  
		cal.add(GregorianCalendar.YEAR, 1);//在年上加1  
		}catch(Exception e){
			e.printStackTrace();
		}
	return cal.getTime();  
	}  
	  /**
	   * 获取当前季度 
	   * 
	   * @return
	   */
	public static int getQuarter(){  
	Calendar cal = Calendar.getInstance();  
	int month = cal.get(Calendar.MONTH);  
	return month/3;  
	}  
	
	public static void main(String[] args) {
		//System.out.println(DateUtil.format(DateUtil.addDay(new Date(), 45),"yyyy-MM-dd"));
		//System.out.println(DateUtil.format(new Date()));
		System.out.println(getToday());
		System.out.println(getDateLongTime());
		DateUtil.setDefaultDatePattern("yyyy-MM-dd HH:mm:ss");
		System.out.println(getToday());
	}
}




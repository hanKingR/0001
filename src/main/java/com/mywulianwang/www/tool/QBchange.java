package com.mywulianwang.www.tool;

public class QBchange{
	
	//全角转为半角
	public   static  String change(String QJstr) {
		char  c[]  =  QJstr.toCharArray();
		for  ( int  i  = 0 ; i  <  c.length; i ++ ) {
			if  (c[i]  =='\u3000'){
				c[i] = ' ';
			}else if (c[i]  >'\uFF00'&&c[i]<'\uFF5F' ) {
				c[i]  =  ( char ) (c[i]  -   65248 );
			}
		}
		String returnString  =   new  String(c);
		return  returnString;

	}
	
	//半角转为全角
	public   static  String ToSBC(String BJstr) {
		char  c[]  =  BJstr.toCharArray();
		for  ( int  i  =   0 ; i  <  c.length; i ++ ) {
			if  (c[i]  ==' ') {
				c[i]  = '\u3000' ;
			}else if  (c[i]  < '\177' ) {
				c[i]  = ( char ) (c[i]  +   65248 );
			}
		}
		return   new  String(c);
	}
} 

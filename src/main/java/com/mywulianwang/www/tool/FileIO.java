package com.mywulianwang.www.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.jsp.JspWriter;

@SuppressWarnings("all")
public class FileIO {

    private FileIO() {
    }

    /**
     * 读取文件内容(static)
     * @param filename	读取的文件名
     * @return String	文件的字符串代码
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFile(String filename) throws FileNotFoundException, IOException {
      return readFile(filename, false);
    }

    /**
     * 读取文件内容(static)
     * @param filename	读取的文件名
     * @return String	文件的字符串代码
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readHtmlFile(String filename) throws FileNotFoundException, IOException {
      return readFile(filename, true);
    }

    /**
     * 读取文件内容(static)
     * @param filename	读取的文件名
     * @return String	文件的字符串代码
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String readFile(String filename, boolean isHtml) throws FileNotFoundException, IOException {
        String return_str = "";
        StringBuffer sb = new StringBuffer();

        FileReader fr = new FileReader(filename);
        LineNumberReader lr = new LineNumberReader(fr);
        while (true) {
            String str = lr.readLine();
            if (str == null) {
                break;
            }
            sb.append(str);
            if(isHtml) {
              sb.append("<br/>");
            } else {
              sb.append("\n");
            }
        }
        lr.close();
        fr.close();
        return_str = sb.toString();
        return return_str;
    }
    public static String readFirstLine(String filePath) throws FileNotFoundException, IOException {
      InputStream is = new FileInputStream(filePath);
       Reader reader = new InputStreamReader(is, "utf-8");
       LineNumberReader lr = new LineNumberReader(reader);
       StringBuffer sb = new StringBuffer();
       String returnStr = "";
       while (true) {
           String str = lr.readLine();
           if (!StringUtil.isEmpty(str)) {
               returnStr =  str;
               break;
           }
       }
       lr.close();
       reader.close();
       is.close();
       return returnStr;

    }


    /**
     * 读取utf-8编码的文本文件
     * @param filePath 文件路径名
     * @throws FileNotFoundException
     * @throws IOException
     * @return String
     */
    public static String readUTF8File(String filePath) throws FileNotFoundException, IOException {
        InputStream is = new FileInputStream(filePath);
        Reader reader = new InputStreamReader(is, "utf-8");
        LineNumberReader lr = new LineNumberReader(reader);
        StringBuffer sb = new StringBuffer();
        while (true) {
            String str = lr.readLine();
            if (str == null) {
                break;
            }
            sb.append(str);
            sb.append("\n");
        }
        lr.close();
        reader.close();
        is.close();
        String text1Str = sb.toString();
        return text1Str;
    }

    /**
     * 模板替换并返回。采用MessageFormat方式，要求模版中需要替换的部分是类似{0}这样的格式
     * @param filename	读取的文件模版
     * @param arguments 替换的参数集合
     * @return String	模板替换出来的字符串
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getModel(String filename, String[] arguments) throws FileNotFoundException,
            IOException {
        String rstr = readFile(filename);
        rstr = MessageFormat.format(rstr, arguments);
        return rstr;
    }

    /**
     * 模板替换并返回
     * @param filename	读取的文件模版
     * @param replaceTbl 替换列表
     * @return String	模板替换出来的字符串
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getHtmlModel(String filename, HashMap replaceTbl) throws FileNotFoundException,
            IOException {

        String rstr = readFile(filename, true);
        Set keys = replaceTbl.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = (String) replaceTbl.get(key);
            rstr = StringReplace.getReplaceString(rstr, key, value);
        }
        return rstr;
    }

    /**
     * 模板替换并返回
     * @param filename	读取的文件模版
     * @param replaceTbl 替换列表
     * @return String	模板替换出来的字符串
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String repalceStr(String rstr, HashMap replaceTbl) throws FileNotFoundException,
            IOException {
        Set keys = replaceTbl.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = (String) replaceTbl.get(key);
            System.out.println("[" + key + "] = " + value);
            rstr = StringReplace.getReplaceString(rstr, key, value);
        }
        return rstr;
    }

    /**
     * 模板替换并返回
     * @param filename	读取的文件模版
     * @param replaceTbl 替换列表
     * @return String	模板替换出来的字符串
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String getModel(String filename, HashMap replaceTbl) throws FileNotFoundException,
            IOException {

        String rstr = readFile(filename);
        Set keys = replaceTbl.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = (String) replaceTbl.get(key);
            rstr = StringReplace.getReplaceString(rstr, key, value);
        }
        return rstr;
    }

    public static void main(String[] args) {
      try {
        String b  = "手机号为" + 111 + "的朋友，为您发送了一条彩信。祝您快乐！";
        getModel("C:/hurray/mms/WEB-INF/model/jslt/head_frame.model", new HashMap());
      }catch(Exception ex) {

      }
    }


    /**
     * 将字符串写入一个文件中(static)
     * @param filename	文件名
     * @param str	写入的字符串
     * @throws IOException
     */
    public static void writeFile(String filename, String str) throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileOutputStream(filename));
            pw.println(str);
        } finally {
            try {
                pw.close();
            } catch (Exception e) {}
        }
    }

    /**
     * 将文件中的内容输出到浏览器中(static)
     * @param filename	读取的文件
     * @param out	浏览器
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void outFile(String filename, JspWriter out) throws FileNotFoundException,
            IOException {
        LineNumberReader lr = null;
        FileReader fr = null;
        try {
            fr = new FileReader(filename);
            lr = new LineNumberReader(fr, 512);
            while (true) {
                String str = lr.readLine();
                if (str == null) {
                    break;
                }
                out.println(str);
            }
            lr.close();
        } finally {
            try {
                lr.close();
                fr.close();
            } catch (Exception e) {}
        }

    }

    /**
     * 将字符串输出到浏览器中(static)
     * @param str	用于输出的字符串
     * @param out	浏览器
     * @throws IOException
     */
    public static void outString(String str, JspWriter out) throws IOException {
        int strLen = str.length();
        int count = 0;
        while (count < (strLen - 1)) {
            char c = str.charAt(count);
            out.print(c);
            count++;
        }
    }

    /**
     * 判断文件是否存在
     * @param directory the file's directory
     * @param filename the file's name
     * @return true:exist; false:not exist
     */
    public static boolean isFileExist(String directory, String filename) {
        File objFile = new File(directory, filename);
        if (!objFile.exists()) {
            return false;
        }
        return true;
    }

}

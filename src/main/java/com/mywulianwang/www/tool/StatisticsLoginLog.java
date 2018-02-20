package com.mywulianwang.www.tool;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
  登录统计
*/
@SuppressWarnings("unchecked")
public class StatisticsLoginLog {
	/**
	 * 统计各个地区登陆失败的情况
	 */
	public static Map<String, Integer> obtainLoginFailMap(
			List<Map<String, String>> login_fail_list) {
		Map<String, Integer> fail_count_info = new HashMap<String, Integer>();
		for(int i = 0; i < login_fail_list.size(); i++){
			Map temp_map = login_fail_list.get(i);
			String divisionName = String.valueOf(temp_map.get("divisionName"));
			if(fail_count_info.get(divisionName) == null){
				fail_count_info.put(divisionName, 1);
			}else{
				int count = fail_count_info.get(divisionName);
				fail_count_info.remove(divisionName);
				fail_count_info.put(divisionName, count +1);
			}
		}
		return fail_count_info;
	}

	/**
	 * 统计各个地区登陆成功的情况
	 */
	public static Map<String, List> obtainLoginSuccessMap(
			List<Map<String, String>> login_success_list) {
		Map<String, Integer> success_count_info = new HashMap<String, Integer>();
		Map<String, List> success_time_info = new HashMap<String, List>();
		for(int i = 0; i < login_success_list.size(); i++){
			Map temp_map = login_success_list.get(i);
			String divisionName = String.valueOf(temp_map.get("divisionName"));
			if(success_count_info.get(divisionName) == null){
				success_count_info.put(divisionName, 1);
				List<Map<String, String>> success_info_list = new ArrayList<Map<String, String>>();
				Map<String, String> map = new HashMap<String, String>();
				map.put("Time-consuming", String.valueOf(temp_map.get("Time-consuming")));
				success_info_list.add(map);
				success_time_info.put(divisionName, success_info_list);
			}else{
				int count = success_count_info.get(divisionName);
				success_count_info.remove(divisionName);
				success_count_info.put(divisionName, count +1);
				List<Map<String, String>> success_info_list = success_time_info.get(divisionName);
				Map<String, String> map = new HashMap<String, String>();
				map.put("Time-consuming", String.valueOf(temp_map.get("Time-consuming")));
				success_info_list.add(map);
				success_time_info.put(divisionName, success_info_list);
			}
		}
		return success_time_info;
	}

	public static List<Map<String, String>> obtainStatisticLoginList(Map<String, Integer> fail_count_info,Map<String, List> success_time_info) {
		Iterator iter = success_time_info.entrySet().iterator(); 
		List<Map<String, String>> statistic_list = new ArrayList<Map<String, String>>();
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    String divisionName = String.valueOf(entry.getKey()); 
		    List<Map<String, String>> success_info_list = (List) entry.getValue();
		    Map<String, String> statistic_map = new HashMap<String, String>();
		    int largest_time = 0;
		    int smallest_time = 0;
		    int sum_value_time = 0;
		    for(int i = 0; i < success_info_list.size(); i++){
		    	Map map = success_info_list.get(i);
		    	int temp_time = Integer.parseInt(String.valueOf(map.get("Time-consuming")));
		    	sum_value_time = sum_value_time + temp_time;
		    	if(i == 0){
		    		largest_time = temp_time;
		    		smallest_time = temp_time;
		    	}else{
		    		if(temp_time > largest_time){
		    			largest_time = temp_time;
		    		}
		    		if(temp_time < smallest_time){
		    			smallest_time = temp_time;
		    		}
		    	}
		    }
		    int average_time = sum_value_time/success_info_list.size();
		    int fail_count = 0;
		    if(fail_count_info.get(divisionName) != null){
		    	fail_count = fail_count_info.get(divisionName);
		    	fail_count_info.remove(divisionName);
		    }
		    statistic_map.put("divisionName", divisionName);
		    statistic_map.put("total_count", String.valueOf(success_info_list.size() + fail_count));
		    statistic_map.put("success_count", String.valueOf(success_info_list.size()));
		    statistic_map.put("fail_count", String.valueOf(fail_count));
		    java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
		    double value = (float)success_info_list.size()/(float)(success_info_list.size() + fail_count) ;
		    statistic_map.put("success_rate", df.format(value * 100)+"%");
		    statistic_map.put("largest_time", String.valueOf(largest_time));
		    statistic_map.put("smallest_time", String.valueOf(smallest_time));
		    statistic_map.put("average_time", String.valueOf(average_time));
		    statistic_list.add(statistic_map);
		}
		Iterator iterator = fail_count_info.entrySet().iterator(); 
		while (iterator.hasNext()) {
			 Map.Entry entry = (Map.Entry) iterator.next();
			 String divisionName = String.valueOf(entry.getKey()); 
			 Integer count_value = (Integer)entry.getValue();
			 Map<String, String> statistic_map = new HashMap<String, String>();
			 
			 statistic_map.put("divisionName", divisionName);
		     statistic_map.put("total_count", String.valueOf(count_value));
		     statistic_map.put("success_count", String.valueOf(0));
		     statistic_map.put("fail_count", String.valueOf(count_value));
		     statistic_map.put("success_rate", "0%");
		     statistic_map.put("largest_time", "");
		     statistic_map.put("smallest_time", "");
		     statistic_map.put("average_time", "");
		     statistic_list.add(statistic_map);
		}
		
		return statistic_list;
	}

	/**
	 * 把code_type_1_info进行遍历，生成MapToList
	 */
	public static List<Map<String, String>> obtainErrorLoginList(String[] code_type_1_info) {
		List<Map<String, String>> login_fail_list_1 = new ArrayList<Map<String, String>>();//需要保留返回页面
		for(int k = 0; k < code_type_1_info.length; k++){
			try {
				HashMap type_1_map = new HashMap<String, String>();
				String[] code_info = code_type_1_info[k].split(";");
				for(int l = 0; l < code_info.length; l++){
					String[] code_sub_info = code_info[l].split(":");
					type_1_map.put(code_sub_info[0], code_sub_info[1]);
				}
				login_fail_list_1.add(type_1_map);
			} catch (Exception e) {
				continue;
			}
			
		}
		 return login_fail_list_1;
	}

	/**
	 * 根据行数实例化一个字符串数组用于存储文件每行的数据
	 */
	public static String[] obtainLoginInfo(String fileString, int lines)
			throws FileNotFoundException, IOException {
		String[] data = new String[lines];
		File f = new File(fileString);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		for (int j = 0; j < lines; j++) {
			String s = br.readLine();
			data[j] = s;
		}
		br.close();
		fr.close();

		/**
		 * 遍历文件的字符串数组，计算出包含登陆信息的行数
		 */
		int codeLines = 0;
		for (int i = 0; i < data.length; i++) {
			String lineStr = data[i];
			if (lineStr.contains("code_type")) {
				codeLines++;
			}
		}
		/**
		 * 根据登陆信息的行数实例化一个数组，并抽取出登陆信息记入loginInfo字符串数组之中
		 */
		String[] loginInfo = new String[codeLines];
		int pointer = 0;
		for (int i = 0; i < data.length; i++) {
			String lineStr = data[i];
			if (lineStr.contains("code_type")) {
				String loginStr = lineStr.split("\\|")[1].trim();
				loginInfo[pointer] = loginStr;
				pointer++;
			}
		}
		return loginInfo;
	}

	public static int calTextLines(String fileString) {
		int lines = 0;
		Reader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileString)));
			while ((((BufferedReader) reader).readLine()) != null) {
				lines++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lines;
	}
}

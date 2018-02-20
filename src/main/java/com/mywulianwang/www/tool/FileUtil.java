package com.mywulianwang.www.tool;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

@SuppressWarnings("all")
public class FileUtil {

	private static String mime;
	private static HashMap icons = null;

	public static String getFileLength(long length) {
		double f = (double) Math.round(((double) length / 1024D) * 100D) / 100D;
		return f + "k";
	}

	public static String getExtension(String name) {
		int i = name.lastIndexOf(46);
		if (i >= 0)
			return name.substring(i + 1).toLowerCase();
		else
			return "";
	}

	public boolean isDir(String name) {
		return (new File(name)).isDirectory();
	}

	public static void initIcons() {
		icons = new HashMap();
		icons.put("application", "binary");
		icons.put("text", "doc");
		icons.put("image", "image");
		icons.put("video", "movie");
		icons.put("audio", "sound");
	}

	public static String trim(String str) {
		if (str == null)
			return null;
		str = str.trim();
		if (str.length() == 0)
			return null;
		else
			return str;
	}

	public static String getFileName(String path) {
		try {
			char separatorChar = '/';
			int index = path.lastIndexOf(separatorChar);
			if (index < 0) {
				separatorChar = '\\';
				index = path.lastIndexOf(separatorChar);
			}
			return path.substring(index + 1);
		} catch (Exception exception) {
			return "Unknown";
		}
	}

	private static boolean isUnicodeFile(String path) throws Exception {
		FileInputStream fi = new FileInputStream(path);
		byte bom[] = new byte[20];
		int n = fi.read(bom);
		for (int i = 0; i < n; i++)
			System.out.println(Integer.toHexString(bom[i]));

		fi.close();
		if (bom[0] == 255 && bom[1] == 254) {
			return true;
		} else {
			System.err.println(new String(bom));
			return false;
		}
	}

	public static void delete(File dir) {
		if (dir.isDirectory()) {
			File files[] = dir.listFiles();
			if (files != null) {
				for (int i = 0; i < files.length; i++)
					delete(files[i]);

			}
			dir.delete();
		} else {
			dir.delete();
		}
	}

	public static void copy(File file, File target) throws IOException {
		if (file.isDirectory())
			return;
		FileInputStream fi = null;
		FileOutputStream fo = null;
		try {
			fi = new FileInputStream(file);
			fo = new FileOutputStream(target);
			byte buff[] = new byte[1024];
			for (int n = 0; (n = fi.read(buff)) != -1;)
				fo.write(buff, 0, n);

		} finally {
			if (fo != null)
				fo.close();
			if (fi != null)
				fi.close();
		}
	}

	public static void main(String args[]) throws Exception {
		System.err.println(isUnicodeFile(args[0]));
	}

}

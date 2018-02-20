package com.winenice.web.util.other;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import magick.ImageInfo;
import magick.MagickApiException;
import magick.MagickException;
import magick.MagickImage;

/**
 * 图片处理
 * 
 * @author Zhiwei Wang(topkaiser@gmail.com)
 * @since 2010-03-16
 */
@SuppressWarnings("unchecked")
public class ImageUtil {      
    /**
     * 缩放图的全局变量
     */
	static int[][] size = {{628,250},{324,124}};
	
    /**
     * 把一张800*800的图片切割成626*800、323*413、180*230、90*115尺码的四张图片
     */
	public static List cutAndZoomImage(String srcDir, String outDir, String srcName,String imgType) {     
		List imgList = new ArrayList();
		try {    
            // 读取源图像
            BufferedImage bi = ImageIO.read(new File(srcDir + srcName));
            int srcHeight = bi.getHeight(); // 源图宽度
            int srcWidth = bi.getWidth(); // 源图高度
            String imgName = "";
            String beginImgName = "";
            if (srcWidth >= 600 && srcHeight >= 600) {
                String[] name = srcName.split("\\.");
                beginImgName = name[0];
                // 循环建立切片
                for(int i = 0; i < size[1].length; i++){
                	String dir_temp = null;
                	String re_src_name = srcDir + srcName;
                	String re_out_name = outDir + beginImgName + "_"+size[1][i] + "_" + size[1][i] + imgType;
                	resetsize(re_src_name, re_out_name , size[1][i], size[1][i]);
                	int left_size = (size[1][i] - size[0][i])/2;
                    dir_temp = outDir + beginImgName + "_" + size[1][i] + "_"+size[1][i] + imgType;
                    imgName = beginImgName + "_" + size[0][i] + "_" + size[1][i] + imgType;
                    String cut_src_name = re_out_name;
                    StringBuffer sb = new StringBuffer();
                	String cut_out_name = sb.append(outDir).append(beginImgName).append("_").append(size[0][i]).append("_").append(size[1][i]).append(imgType).toString();
                    cutImage(cut_src_name, cut_out_name, size[0][i], size[1][i], left_size, 0);
                    File file_i = new File(dir_temp);
                    
                    if(i < (size[1].length - 1)){
                    	file_i.delete(); //生成32*32的小图片用
                    }
                    imgList.add(imgName);
               }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgList;
    } 

	public static void main(String[] args){
		System.out.println(System.getProperty("java.library.path"));
		try {
			cutImage("E:\\1236493LS0-1545U0.png", "E:\\1.png",300,300,0,0);
		} catch (MagickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void resetsize(String picFrom, String picTo, int width, int heigth) {
		try {
			System.setProperty("jmagick.systemclassloader","no");
			System.out.println(System.getProperty("java.library.path"));
			ImageInfo info = new ImageInfo(picFrom);
			MagickImage image = new MagickImage(new ImageInfo(picFrom));
			MagickImage scaled = image.scaleImage(width, heigth);
			scaled.setFileName(picTo);
			scaled.writeImage(info);
		} catch (MagickApiException ex) {
			ex.printStackTrace();
		} catch (MagickException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
     * 切图
     * @param imgPath 源图路径
     * @param toPath  修改图路径
     * @param w
     * @param h
     * @param x
     * @param y
     * @throws MagickException
     */
    public static void cutImage(String imgPath, String toPath, int w, int h, int x, int y) throws MagickException {
        ImageInfo infoS = null;
        MagickImage image = null;
        MagickImage cropped = null;
        Rectangle rect = null;
        try {
            infoS = new ImageInfo(imgPath);
            image = new MagickImage(infoS);
            rect = new Rectangle(x, y, w, h);
            cropped = image.cropImage(rect);
            cropped.setFileName(toPath);
            cropped.writeImage(infoS);   
 
        } finally {
            if (cropped != null) {
                cropped.destroyImages();
            }
        }
    }
}

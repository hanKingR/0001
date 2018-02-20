package com.mywulianwang.www.tool;

import java.awt.Dimension;  
import java.awt.Rectangle;  
import java.text.SimpleDateFormat;  
import java.util.ArrayList;
import java.util.Date;  

import org.apache.struts2.ServletActionContext;

import org.apache.log4j.Logger;

import magick.CompositeOperator;  
import magick.CompressionType;  
import magick.DrawInfo;  
import magick.ImageInfo;  
import magick.MagickException;  
import magick.MagickImage;  
import magick.PixelPacket;  
import magick.PreviewType;
/**
  对图片经行
  压缩 保存 切图 水印 
*/
@SuppressWarnings("all")
public class JmagickHelper {
	private static final Logger logger = Logger.getLogger(JmagickHelper.class); 
	static{  
	    //不能漏掉这个，不然jmagick.jar的路径找不到  
	    System.setProperty("jmagick.systemclassloader","no");  
	}  

    
    /** 
     * 压缩图片 
     * @param filePath 源文件路径 
     * @param toPath   缩略图路径 
     * @param thumbnailWideth   缩略图宽度
     * @param thumbnailHeight   缩略图高度
     */ 
    public static void createThumbnail(String filePath, String toPath, Integer thumbnailWideth, Integer thumbnailHeight){  
    	try {
    		String disUrl = ServletActionContext.getServletContext().getRealPath(toPath);
			ImageInfo info = null;  
			MagickImage image = null;  
			Dimension imageDim = null;  
			MagickImage scaled = null;
			try{
			    info = new ImageInfo(filePath);
			    image = new MagickImage(info);
			    imageDim = image.getDimension(); 
			    scaled = image.scaleImage(thumbnailWideth, thumbnailHeight);//小图片文件的大小.
			    scaled.setFileName(disUrl); 
			    scaled.writeImage(info); 
			}finally{  
			    if(scaled != null){  
			        scaled.destroyImages();  
			    }  
			}
		} catch (MagickException e) {
			logger.info("jxl_ createThumbnail is error:"+ e.getMessage());
		}    
    }  
    /** 
     * 保存图片 
     * @param filePath 源文件路径 
     * @param toPath   缩略图路径 
     */ 
    public static void upPic(String filePath, String toPath){  
    	try {
			filePath = ServletActionContext.getServletContext().getRealPath(filePath);
			ImageInfo info = null;  
			MagickImage image = null;  
			Dimension imageDim = null;  
			MagickImage scaled = null;  
			try{  
			    info = new ImageInfo(filePath);  
			    image = new MagickImage(info);  
			    imageDim = image.getDimension();  
			    int wideth = imageDim.width;  
			    int height = imageDim.height;  
			    scaled = image.scaleImage(wideth, height);//小图片文件的大小.  
			    scaled.setFileName(toPath);  
			    scaled.writeImage(info);  
			}finally{  
			    if(scaled != null){  
			        scaled.destroyImages();
			        image.destroyImages();
			    }  
			}
		} catch (MagickException e) {
			logger.info("jxl_ upPic is error:"+ e.getMessage());
		}    
    }  
    /** 
     * 水印(图片logo) 
     * @param filePath  源文件路径 
     * @param toImg     修改图路径 
     * @param logoPath  logo图路径 
     * @throws MagickException 
     */ 
    public static void initLogoImg(String filePath, String toImg, String logoPath) throws MagickException {  
        ImageInfo info = new ImageInfo();  
        MagickImage fImage = null;  
        MagickImage sImage = null;  
        MagickImage fLogo = null;  
        MagickImage sLogo = null;  
        Dimension imageDim = null;  
        Dimension logoDim = null;  
        try {  
            fImage = new MagickImage(new ImageInfo(filePath));  
            imageDim = fImage.getDimension();  
            int width = imageDim.width;  
            int height = imageDim.height;  
            if (width > 660) {  
                height = 660 * height / width;  
                width = 660;  
            }  
            sImage = fImage.scaleImage(width, height);  
              
            fLogo = new MagickImage(new ImageInfo(logoPath));  
            logoDim = fLogo.getDimension();  
            int lw = width / 8;  
            int lh = logoDim.height * lw / logoDim.width;  
            sLogo = fLogo.scaleImage(lw, lh);  

            sImage.compositeImage(CompositeOperator.AtopCompositeOp, sLogo,  width-(lw + lh/10), height-(lh + lh/10));  
            sImage.setFileName(toImg);  
            sImage.writeImage(info);  
        } finally {  
            if(sImage != null){  
                sImage.destroyImages();  
            }  
        }  
    }  
      
    /** 
     * 水印(文字) 
     * @param filePath 源文件路径 
     * @param toImg    修改图路径 
     * @param text     名字(文字内容自己随意) 
     * @throws MagickException 
     */ 
    public static void initTextToImg(String filePath, String toImg,  String text) throws MagickException{  
            ImageInfo info = new ImageInfo(filePath);  
            if (filePath.toUpperCase().endsWith("JPG") || filePath.toUpperCase().endsWith("JPEG")) {  
                info.setCompression(CompressionType.JPEGCompression); //压缩类别为JPEG格式  
                info.setPreviewType(PreviewType.JPEGPreview); //预览格式为JPEG格式  
                info.setQuality(95);  
            }  
            MagickImage aImage = new MagickImage(info);  
            Dimension imageDim = aImage.getDimension();  
            int wideth = imageDim.width;  
            int height = imageDim.height;  
            if (wideth > 660) {  
                height = 660 * height / wideth;  
                wideth = 660;  
            }  
            int a = 0;  
            int b = 0;  
            String[] as = text.split("");  
            for (String string : as) {  
                if(string.matches("[\u4E00-\u9FA5]")){  
                    a++;  
                }  
                if(string.matches("[a-zA-Z0-9]")){  
                    b++;  
                }  
            }  
            int tl = a*12 + b*6 + 300;  
            MagickImage scaled = aImage.scaleImage(wideth, height);  
            if(wideth > tl && height > 5){  
                DrawInfo aInfo = new DrawInfo(info);  
                aInfo.setFill(PixelPacket.queryColorDatabase("white"));  
                aInfo.setUnderColor(new PixelPacket(0,0,0,100)); //设置字体的背景颜色 
                aInfo.setPointsize(12);  
                //解决中文乱码问题,自己可以去随意定义个自己喜欢字体，我在这用的微软雅黑  
                String fontPath = "C:/WINDOWS/Fonts/MSYH.TTF";  
                aInfo.setFont(fontPath);  
                aInfo.setTextAntialias(true);  
                aInfo.setOpacity(0);  //透明0--100
                aInfo.setText("　" + text + "于　" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "　上传于XXXX网，版权归作者所有！");  
                aInfo.setGeometry("+" + (wideth-tl) + "+" + (height-5));  
                scaled.annotateImage(aInfo);  
            }  
            scaled.setFileName(toImg);  
            scaled.writeImage(info);  
            scaled.destroyImages();  
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
    public static void cutImg(String imgPath, String toPath, int w, int h, int x, int y) throws MagickException {  
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
    
    
    /**
    * 把两张图片合成为一张图
    * @param src1	源文件路径1
    * @param src2	源文件路径1
    * @param output	合成后的输出路径
    * @param x		合成图片时src2所在output中的横坐标
    * @param y		合成图片时src2所在output中的纵坐标
    * @throws MagickException
    */
    public void combination(String src1,String src2,int x,int y,String output) throws MagickException {

       ImageInfo info = new ImageInfo(src1);
       MagickImage image = new MagickImage(info);
       ImageInfo info2 = new ImageInfo(src2);
       info2.setTile("abcde");
       info.setTexture("ggggg");
       MagickImage image2 = new MagickImage(info2);  
       image.compositeImage(3, image2, x, y);
       // image.addNoiseImage(3);
       int out_width = 500;//合成图片output的宽度
       int out_height = 316;//合成图片output的宽度
       MagickImage scaled = image.scaleImage(out_width, out_height);// 小图片文件的大小.
       scaled.setFileName(output);
       scaled.writeImage(info);
    }
}  


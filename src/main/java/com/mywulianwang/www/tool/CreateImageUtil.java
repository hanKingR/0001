package com.mywulianwang.www.tool;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("all")
public class CreateImageUtil {

	private static final Logger logger = Logger
			.getLogger(CreateImageUtil.class);
	private static Component component = new Canvas();
	// ".pcx","tga",".tif"这三种格式目前还不支持；
	// 这些定义的格式经过我测试过是可以支持的。
	private static String[] imageFormatArray = new String[] { ".jpg", ".jpeg",
			".gif", ".png", ".bmp" };

	/**
	 * 
	 * @param srcImg
	 *            原图
	 * @param targetImg
	 *            新图
	 * @param width
	 *            新图片宽度
	 * @param height
	 *            新图片高度
	 * @param quality
	 *            压缩度 默认1 0.95变小
	 * @param degree
	 *            旋转参数 0不旋转 1 旋转90度 2 旋转180 3 旋转270度 都是顺时针
	 * @param compreMode
	 *            0:等比压缩，1：按最小宽高压缩
	 * @return
	 */
	public static boolean convert(String srcImg, String targetImg, int width,
			int height, float quality, int degree, int compreMode) {
		Graphics2D graphics2D = null;
		try {
			Image image = null;
			String ext = setImgProperties(new File(srcImg));
			if (ext.equals("gif") || ext.equals("bmp")) {
				image = ImageIO.read(new File(srcImg));
			} else {
				image = Toolkit.getDefaultToolkit().createImage(srcImg);
				MediaTracker mediaTracker = new MediaTracker(new Container());
				mediaTracker.addImage(image, 0);
				mediaTracker.waitForID(0);
			}
			CompreImageEntry comEntry = new CompreImageEntry(image, width,
					height, compreMode, quality, degree);
			BufferedImage thumbImage = new BufferedImage(comEntry.getWidth(),
					comEntry.getHeight(), BufferedImage.TYPE_INT_RGB);
			graphics2D = thumbImage.createGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			if (degree > 0) {
				graphics2D.rotate(Math.toRadians(90 * degree), comEntry
						.getWidth() / 2, comEntry.getHeight() / 2);
				if (comEntry.isTranslateFlg()) {
					graphics2D.translate(comEntry.getTranslateX(), comEntry
							.getTranslateY());
				}
			}

			graphics2D.drawImage(image, comEntry.getOffsetX(), comEntry
					.getOffsetY(), comEntry.compreWidth(), comEntry
					.compreHeight(), null);
			// save thumbnail image to OUTFILE
			BufferedOutputStream out = new BufferedOutputStream(
					new FileOutputStream(targetImg));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder
					.getDefaultJPEGEncodeParam(thumbImage);
			param.setQuality(quality, false);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(thumbImage);
			out.close();
		} catch (Throwable e) {
			return false;
		} finally {
			try {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
			} catch (Exception e) {
			}
		}

		return true;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 将目录下的所有图像进行放大缩小
	 * 
	 * @param strDir
	 *            图像的目录
	 * @param zoomRatio
	 *            放大缩小的倍率
	 * @param rebuild
	 *            是否重新创建，即已经存在的图像是否覆盖重建
	 * @throws Exception
	 *             Exception
	 */
	public void zoom(String strDir, double zoomRatio, boolean rebuild)
			throws Exception {
		File fileDir = new File(strDir);
		if (!fileDir.exists()) {
			logger.warn("Not exist:" + strDir);
			return;
		}
		String dirTarget = strDir + "/Zoom" + zoomRatio;
		File fileTarget = new File(dirTarget);
		if (!fileTarget.exists()) {
			fileTarget.mkdir();
		}
		File[] files = fileDir.listFiles();
		StringBuilder stringBuilder;
		for (File file : files) {
			String fileFullName = file.getCanonicalPath();
			String fileShortName = file.getName();
			if (!new File(fileFullName).isDirectory())// 排除二级目录，如果想就再递归一次，这里省略
			{
				if (isZoomAble(fileShortName)) {
					if (logger.isInfoEnabled()) {
						logger.info("Begin Zoom:" + fileFullName);
					}
					stringBuilder = new StringBuilder();
					stringBuilder.append(dirTarget).append("/").append(
							fileShortName);
					if (!new File(stringBuilder.toString()).exists() || rebuild) {
						try {
							createZoomSizeImage(fileFullName, stringBuilder
									.toString(), zoomRatio);
						} catch (Exception e) {
						}
					}
					if (logger.isInfoEnabled()) {
						logger.info("End Zoom:" + fileFullName);
					}
				} else {
					logger.warn("Can't Zoom:" + fileFullName);
				}
			}
		}
	}

	/**
	 * 校验图像文件的格式是否可以进行缩放
	 * 
	 * @param fileName
	 *            fileName
	 * @return boolean iszoom able
	 */
	public boolean isZoomAble(String fileName) {
		boolean result = false;
		for (String imageFormat : imageFormatArray) {
			if (fileName.toLowerCase().lastIndexOf(imageFormat) == (fileName
					.length() - imageFormat.length())) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * 按比例进行放大缩小图像，zoomRatio = 1为原大，zoomRatio > 1为放大，zoomRatio < 1 为缩小
	 * 
	 * @param fileName
	 *            源文件的名称(路径)
	 * @param fileNameTarget
	 *            目标文件名
	 * @param zoomRatio
	 *            缩放比例
	 * @throws Exception
	 *             exception
	 */
	public void createZoomSizeImage(String fileName, String fileNameTarget,
			double zoomRatio) throws Exception {
		Image image = ImageIO.read(new File(fileName));
		int width = new Double(image.getWidth(null) * zoomRatio).intValue();
		int height = new Double(image.getHeight(null) * zoomRatio).intValue();
		AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(
				width, height);
		FilteredImageSource filteredImageSource = new FilteredImageSource(image
				.getSource(), areaAveragingScaleFilter);
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(component.createImage(filteredImageSource), 0, 0,
				null);
		ImageIO.write(bufferedImage, "JPEG", new File(fileNameTarget));
	}

	/**
	 * 旋转图片为指定角度
	 * 
	 * @param bufferedimage
	 *            目标图像
	 * @param degree
	 *            旋转角度
	 * @return
	 */
	public static BufferedImage rotateImage(final BufferedImage bufferedimage,
			final int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(bufferedimage, 0, 0, null);
		graphics2d.dispose();
		return img;
	}

	/**
	 * 根据四点座标截图
	 * 
	 * @param fileName
	 *            源文件的名称(路径)
	 * @param fileNameTarget
	 *            目标文件名
	 * @param x
	 *            x座标
	 * @param y
	 *            y座标
	 * @param w
	 *            w座标
	 * @param h
	 *            h座标
	 * @throws Exception
	 */
	public void cropImage(String fileName, String fileNameTarget, int x, int y,
			int w, int h) throws Exception {
		Image sourceImage = ImageIO.read(new File(fileName));

		ImageFilter cropFilter = new CropImageFilter(x, y, w, h);// 
		FilteredImageSource filteredImageSource = new FilteredImageSource(
				sourceImage.getSource(), cropFilter);
		BufferedImage bufferedImage = new BufferedImage(w, h,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics graphics = bufferedImage.createGraphics();
		graphics.drawImage(component.createImage(filteredImageSource), 0, 0,
				null);
		ImageIO.write(bufferedImage, "JPEG", new File(fileNameTarget));
	}

	/**
	 * 复制文件
	 * 
	 * @param source
	 *            源文件
	 * @param destination
	 *            目标文件名
	 */
	public void copyFile(String source, String destination) {
		File f = new File(destination
				.substring(0, destination.lastIndexOf("/")));
		if (!f.isDirectory()) {
			f.mkdirs();
		}
		FileInputStream fis = null;
		// 输出流
		FileOutputStream fos = null;
		// 用路径 “d:/a.jpg” 代替f File f=new File( “d:/a.jpg”)
		try {
			fis = new FileInputStream(source);
			fos = new FileOutputStream(destination);
			byte bytes[] = new byte[1024];
			int n = 0; // 记录实际读取到的字节数
			while ((n = fis.read(bytes)) != -1) {
				// 输出到指定文件
				fos.write(bytes, 0, n);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		} finally {
			// 关闭流
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}
		}
	}

	/**
	 * 把图片印刷到图片上
	 * 
	 * @param pressImg
	 *            水印文件
	 * @param targetImg
	 *            目标文件
	 * @param x
	 *            偏移量x
	 * @param y
	 *            偏移量y
	 */
	public static void pressImage(String pressImg, String targetImg, int x,
			int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);

			// 水印文件
			File _filebiao = new File(pressImg);
			Image src_biao = ImageIO.read(_filebiao);
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.drawImage(src_biao, wideth - wideth_biao - x, height
					- height_biao - y, wideth_biao, height_biao, null);
			// /
			g.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 打印文字水印图片
	 * 
	 * @param pressText
	 *            文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            偏移量x
	 * @param y
	 *            偏移量y
	 */

	public static void pressText(String pressText, String targetImg,
			String fontName, int fontStyle, String color, int fontSize, int x,
			int y) {
		try {
			File _file = new File(targetImg);
			Image src = ImageIO.read(_file);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics graphics = image.createGraphics();
			graphics.drawImage(src, 0, 0, wideth, height, null);
			// String s="www.qhd.com.cn";
			int r = Integer.parseInt(color.substring(0, 2), 16);
			int g = Integer.parseInt(color.substring(2, 4), 16);
			int b = Integer.parseInt(color.substring(4), 16);

			graphics.setColor(new Color(r, g, b));
			graphics.setFont(new Font(fontName, fontStyle, fontSize));

			graphics.drawString(pressText, wideth - fontSize - x, height
					- fontSize / 2 - y);
			graphics.dispose();
			FileOutputStream out = new FileOutputStream(targetImg);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(image);
			out.close();
		} catch (Exception e) {
		}
	}

	/**
	 * 读取GIF文件，并进行缩放，存放于BufferedImage数组中
	 * 
	 * @param inputFileName
	 *            源图像文件
	 * @param zoomRatio
	 *            zoom ratio > 1 zoom in; < 1 zoom out;
	 * @return BufferedImage Array
	 * @throws IOException
	 *             IOException
	 */
	public BufferedImage[] readGifFile(String inputFileName, double zoomRatio)
			throws IOException {
		Iterator imageReaders = ImageIO.getImageReadersBySuffix("GIF");
		if (!imageReaders.hasNext()) {
			throw new IOException("no ImageReaders for GIF");
		}
		ImageReader imageReader = (ImageReader) imageReaders.next();
		File file = new File(inputFileName);
		if (!file.exists()) {
			throw new IOException("no file: " + file.getName());
		}
		imageReader.setInput(ImageIO.createImageInputStream(file));
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		for (int i = 0; true; ++i) {
			try {
				Image image = imageReader.read(i);
				int width = new Double(image.getWidth(null) * zoomRatio)
						.intValue();
				int height = new Double(image.getHeight(null) * zoomRatio)
						.intValue();
				if (width > 0 && height > 0) {
					AreaAveragingScaleFilter areaAveragingScaleFilter = new AreaAveragingScaleFilter(
							width, height);
					FilteredImageSource filteredImageSource = new FilteredImageSource(
							image.getSource(), areaAveragingScaleFilter);
					BufferedImage bufferedImage = new BufferedImage(width,
							height, BufferedImage.TYPE_3BYTE_BGR);
					Graphics graphics = bufferedImage.createGraphics();
					graphics.drawImage(component
							.createImage(filteredImageSource), 0, 0, null);
					images.add(bufferedImage);
				}
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		return images.toArray(new BufferedImage[images.size()]);
	}

	/**
	 * 根据BufferedImage数组的数据，写入到GIF文件中去
	 * 
	 * @param images
	 *            source images to put into GIF
	 * @param outputFileName
	 *            target file name
	 * @throws java.io.IOException
	 *             IOException
	 */
	public void writeGifFile(BufferedImage[] images, String outputFileName)
			throws IOException {
		Iterator imageWriters = ImageIO.getImageWritersBySuffix("GIF");
		if (!imageWriters.hasNext()) {
			throw new IOException("no ImageWriters for GIF");
		}
		ImageWriter imageWriter = (ImageWriter) imageWriters.next();
		File file = new File(outputFileName);
		file.delete();
		imageWriter.setOutput(ImageIO.createImageOutputStream(file));
		if (imageWriter.canWriteSequence()) {
			if (logger.isInfoEnabled()) {
				logger.info("Using writeToSequence for format GIF");
			}
			imageWriter.prepareWriteSequence(null);
			for (BufferedImage image : images) {
				imageWriter.writeToSequence(new IIOImage(image, null, null),
						null);
			}
			imageWriter.endWriteSequence();
		} else {
			if (logger.isInfoEnabled()) {
				logger.info("cross fingers for format GIF");
			}
			for (BufferedImage image : images) {
				imageWriter.write(image);
			}
		}
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param fileName
	 *            路径
	 * @return
	 */
	public static int getWidth(String fileName) {
		int width = 0;
		Image image;
		try {
			image = ImageIO.read(new File(fileName));
			width = image.getWidth(null);
		} catch (IOException e) {
		}
		return width;
	}

	/**
	 * 获取图片高度
	 * 
	 * @param fileName
	 *            高度
	 * @return
	 */
	public static int getHeight(String fileName) {
		int height = 0;
		Image image;
		try {
			image = ImageIO.read(new File(fileName));
			height = image.getHeight(null);
		} catch (IOException e) {
		}
		return height;
	}

	public static String setImgProperties(File f) {
		InputStream in = null;
		String ext = "";
		try {
			in = new FileInputStream(f);
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.setInput(in);
			imageInfo.setDetermineImageNumber(true);
			imageInfo.setCollectComments(false);
			if (imageInfo.check()) {
				ext = imageInfo.getFormatName().toLowerCase();

			}
		} catch (Exception e) {

		} finally {
			try {
				in.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return ext;
	}

}

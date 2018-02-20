package com.mywulianwang.www.tool;

import java.awt.Image;

public class CompreImageEntry {
	int width;// 压缩以后的尺寸0,表示不限

	int height;// 压缩以后的尺寸0,表示不限

	int cutMode; // 剪切属性0:等比压缩，1：按最小宽高压缩

	float quality; // 品质

	float degree; // 旋转角度0：表示不旋转ת

	int originalWidth;

	int originalHeight;

	public CompreImageEntry(Image image, int width, int height, int cutMode, float quality, int degree) {
		if (degree == 1 || degree == 3) {
			originalHeight = image.getWidth(null);
			originalWidth = image.getHeight(null);
		} else {
			originalWidth = image.getWidth(null);
			originalHeight = image.getHeight(null);
		}
		boolean f = false;
		if (width == 0 || height == 0) {
			f = true;
		}
		if (width == 0 && height == 0) {
			this.width = width = originalWidth;
			this.height = height = originalHeight;
		} else if (width == 0) {
			width = (int) (float) height * originalWidth / originalHeight;
		} else if (height == 0) {
			height = (int) (float) width * originalHeight / originalWidth;
		}
		if (cutMode == 0) {
			if (originalWidth < width && originalHeight < height) {
				this.width = originalWidth;
				this.height = originalHeight;
			} else if (!f) {
				if ((float) width / originalWidth > (float) height / originalHeight) {
					this.width = (int) (originalWidth * (float) height / originalHeight);
					this.height = height;
				} else {
					this.width = width;
					this.height = (int) (originalHeight * (float) width / originalWidth);
				}
			} else {
				this.width = width;
				this.height = height;
			}
		} else {
			this.width = width;
			this.height = height;
		}
		this.cutMode = cutMode;
		this.quality = quality;
		this.degree = degree;
	}

	public int getCutMode() {
		return cutMode;
	}

	public void setCutMode(int cutMode) {
		this.cutMode = cutMode;
	}

	public float getDegree() {
		return degree;
	}

	public void setDegree(float degree) {
		this.degree = degree;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getQuality() {
		return quality;
	}

	public void setQuality(float quality) {
		this.quality = quality;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getOffsetX() {
		return cutMode == 0 ? 0 : degree == 1 || degree == 3 ? (int) Math.floor((height - compreWidth()) / 2.0) : (int) Math
				.floor((width - compreWidth()) / 2.0);
	}

	public int getOffsetY() {
		return cutMode == 0 ? 0 : degree == 1 || degree == 3 ? (int) Math.floor((width - compreHeight()) / 2.0) : (int) Math
				.floor((height - compreHeight()) / 2.0);
	}

	public int getTranslateX() {
		int x = 0;
		if ((height % 2 == 1 || width % 2 == 1) && degree == 3) {
			x = width - 1;
		} else if (height % 2 == 1 && width % 2 == 0 && degree == 1) {
			x = width + 1;
		} else {
			x = width;
		}

		return (int) Math.floor((x - height) / 2.0);
	}

	public int getTranslateY() {
		int y = 0;
		if (height % 2 == 0 && width % 2 == 1 && degree == 3) {
			y = height + 1;
		} else if ((height % 2 == 1 || width % 2 == 1) && degree == 1) {
			y = height - 1;
		} else {
			y = height;
		}

		return (int) Math.floor((y - width) / 2.0);

	}

	public boolean isTranslateFlg() {
		return (degree == 1 || degree == 3) && originalWidth != originalHeight;
	}

	public int compreWidth() {
		if (cutMode == 0) {
			return degree == 1 || degree == 3 ? height : width;
		}
		int w = 0;
		if (degree == 1 || degree == 3) {
			if ((float) width / originalWidth > (float) height / originalHeight) {
				w = (int) Math.ceil((float) width * originalHeight / originalWidth);
			} else {
				w = height;
			}
		} else {
			if ((float) width / originalWidth < (float) height / originalHeight) {
				w = (int) Math.ceil((float) height * originalWidth / originalHeight);
			} else {
				w = width;
			}
		}

		return w;
	}

	public int compreHeight() {
		if (cutMode == 0) {
			return degree == 1 || degree == 3 ? width : height;
		}
		int h = 0;
		if (degree == 1 || degree == 3) {
			if ((float) width / originalWidth < (float) height / originalHeight) {
				h = (int) Math.ceil((float) height * originalWidth / originalHeight);
			} else {
				h = width;
			}
		} else {
			if ((float) width / originalWidth > (float) height / originalHeight) {
				h = (int) Math.ceil((float) width * originalHeight / originalWidth);
			} else {
				h = height;
			}
		}
		return h;
	}
}
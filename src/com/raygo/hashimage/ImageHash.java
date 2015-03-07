package com.raygo.hashimage;

import java.io.ByteArrayInputStream;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;

public class ImageHash {

	/**
	 * ͼƬ����
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	private Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}


	/**
	 * ����ͼƬָ��
	 * 
	 * @param filename
	 *            �ļ���
	 * @return ͼƬָ��
	 */
	public static String imageToHash(Bitmap bitmap) {

		int width = 16;
		int height = 16;

		// ��һ������С�ߴ硣
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

		// �ڶ�������ɫ�ʡ�
		int[] pixels = new int[width * height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = newbitmap.getPixel(i, j);
				int _red = (rgb >> 16) & 0xFF;
				int _green = (rgb >> 8) & 0xFF;
				int _blue = (rgb) & 0xFF;
				pixels[i * height + j] = (int) (0.3 * _red + 0.59 * _green + 0.11 * _blue);
			}
		}

		// ������������ƽ��ֵ��
		// ��������64�����صĻҶ�ƽ��ֵ��
		float m = 0;
		for (int i = 0; i < pixels.length; ++i) {
			m += pixels[i];
		}
		int avgPixel = (int) (m / pixels.length);

		// ���Ĳ����Ƚ����صĻҶȡ�
		// ��ÿ�����صĻҶȣ���ƽ��ֵ���бȽϡ����ڻ����ƽ��ֵ����Ϊ1��С��ƽ��ֵ����Ϊ0��
		int[] comps = new int[width * height];
		for (int i = 0; i < comps.length; i++) {
			if (pixels[i] >= avgPixel) {
				comps[i] = 1;
			} else {
				comps[i] = 0;
			}
		}

		// ���岽�������ϣֵ
		StringBuffer hashCode = new StringBuffer();
		for (int i = 0; i < comps.length; i += 4) {
			int result = comps[i] * (int) Math.pow(2, 3) + comps[i + 1]
					* (int) Math.pow(2, 2) + comps[i + 2]
					* (int) Math.pow(2, 1) + comps[i + 3];
			hashCode.append(binaryToHex(result));
		}

		// �õ�ָ���Ժ󣬾Ϳ��ԶԱȲ�ͬ��ͼƬ������64λ���ж���λ�ǲ�һ���ġ�
		return hashCode.toString();
	}

	/**
	 * ������תΪʮ������
	 * 
	 * @param int binary
	 * @return char hex
	 */
	private static char binaryToHex(int binary) {
		char ch = ' ';
		switch (binary) {
		case 0:
			ch = '0';
			break;
		case 1:
			ch = '1';
			break;
		case 2:
			ch = '2';
			break;
		case 3:
			ch = '3';
			break;
		case 4:
			ch = '4';
			break;
		case 5:
			ch = '5';
			break;
		case 6:
			ch = '6';
			break;
		case 7:
			ch = '7';
			break;
		case 8:
			ch = '8';
			break;
		case 9:
			ch = '9';
			break;
		case 10:
			ch = 'a';
			break;
		case 11:
			ch = 'b';
			break;
		case 12:
			ch = 'c';
			break;
		case 13:
			ch = 'd';
			break;
		case 14:
			ch = 'e';
			break;
		case 15:
			ch = 'f';
			break;
		default:
			ch = ' ';
		}
		return ch;
	}

	/**
	 * ����ɫͼת��Ϊ�Ҷ�ͼ
	 * 
	 * @param img
	 *            λͼ
	 * @return ����ת���õ�λͼ
	 */
	public static Bitmap convertGreyImg(Bitmap img) {
		int width = img.getWidth(); // ��ȡλͼ�Ŀ�
		int height = img.getHeight(); // ��ȡλͼ�ĸ�

		int[] pixels = new int[width * height]; // ͨ��λͼ�Ĵ�С�������ص�����

		img.getPixels(pixels, 0, width, 0, 0, width, height);
		int alpha = 0xFF << 24;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int grey = pixels[width * i + j];

				int red = ((grey & 0x00FF0000) >> 16);
				int green = ((grey & 0x0000FF00) >> 8);
				int blue = (grey & 0x000000FF);

				grey = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
				grey = alpha | (grey << 16) | (grey << 8) | grey;
				pixels[width * i + j] = grey;
			}
		}
		Bitmap result = Bitmap.createBitmap(width, height, Config.RGB_565);
		result.setPixels(pixels, 0, width, 0, 0, width, height);
		return result;
	}
}


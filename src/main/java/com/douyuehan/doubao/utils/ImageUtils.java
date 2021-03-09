package com.douyuehan.doubao.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageUtils {

	// 图片上传最大大小 byte
	public static final int MAX_IMAGE_SIZE = 200 * 1024;
	// 项目海报上传最大大小 byte
	public static final int MAX_IMAGE_SIZE_PROJECT = 500 * 1024;
	//项目推荐海报
	public static final int RECOMMEND_HEIGHT = 175*2;
	public static final int RECOMMEND_WIDTH= 370*2;
	// 图片压缩 宽高
	public static final int COMPRESSION_WIDTH = 960;
	public static final int COMPRESSION_HEIGHT = 640;

	public static final int COMPRESSION_WIDTH_PROJECT = 1280;
	public static final int COMPRESSION_HEIGHT_PROJECT = 720;

	// 缩略图默认裁剪尺寸
	public static final int DEAFAULT_WIDTH = 420;
	public static final int DEAFAULT_HEIGHT = 420;
	// 缩略图后缀
	public static final String SUFFIX = "_TN";

	public static void main(String[] args) {
		// toFile(cut(getByte("d:/abc.jpg"),100, 100, 400 ,400),
		// "d:/cut_abc.jpg");
		List<FontMark> list = new ArrayList<>();
		FontMark mark = new FontMark();
		mark.setFontName("宋体");
		mark.setColor(Color.BLACK);
		mark.setFontSize(20);
		mark.setFontStyle(Font.PLAIN);
		mark.setText("我是水印1");
		mark.setX(0);
		mark.setY(0);
		list.add(mark);

		FontMark mark1 = new FontMark();
		mark1.setFontName("宋体");
		mark1.setColor(Color.WHITE);
		mark1.setFontSize(20);
		mark1.setFontStyle(Font.PLAIN);
		mark1.setText("12345678901234567890");
		mark1.setX(100);
		mark1.setY(100);
		list.add(mark1);

		// toFile(resize(getByte("C:\\Users\\mayun\\Desktop\\upload\\1 (6).jpg"),
		// 400, 400), "C:\\Users\\mayun\\Desktop\\upload\\new2.jpg");

		getWHSize(getByte("C:\\Users\\mayun\\Desktop\\upload\\1 (6).jpg"));

	}

	/**
	 * @Descripiton 获取图片的长宽
	 * @param bytes
	 *            [width,height]
	 * @return
	 *
	 * @author don
	 * @date 2015年9月11日 下午1:46:55
	 */
	public static int[] getWHSize(byte[] bytes) {
		int[] result = new int[2];

		BufferedImage sourceImg;
		try {
			sourceImg = ImageIO.read(new ByteArrayInputStream(bytes));
			result[0] = sourceImg.getWidth();
			result[1] = sourceImg.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static byte[] mark(byte[] bytes, List<FontMark> fontMarkList) {
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		Thumbnails.Builder builder = Thumbnails.of(bai).scale(1).outputQuality(0.9f);
		for (final FontMark mark : fontMarkList) {
			builder.watermark(new Position() {
				@Override
				public Point calculate(int enclosingWidth,
									   int enclosingHeight,
									   int width,
									   int height,
									   int insetLeft,
									   int insetRight,
									   int insetTop,
									   int insetBottom) {
					return new Point(mark.getX(), mark.getY());
				}
			}, ImageUtils.fontImage(mark.getText(), mark.getFontName(), mark.getFontStyle(), mark.getFontSize(), mark.getColor()), 1f);
		}
		try {
			builder.toOutputStream(bao);
			return bao.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bai.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] cut(byte[] bytes, int x1, int y1, int x2, int y2, int outputWidth, int outputHeight) {
		int width = x2 - x1;
		int height = y2 - y1;
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			Thumbnails.of(bai).sourceRegion(x1, y1, width, height).size(outputWidth, outputHeight).outputQuality(0.9f).toOutputStream(bao);
			return bao.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bai.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] cut(byte[] bytes, int x1, int y1, int x2, int y2) {
		int width = x2 - x1;
		int height = y2 - y1;
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			Thumbnails.of(bai).sourceRegion(x1, y1, width, height).scale(1).outputQuality(0.9f).toOutputStream(bao);
			return bao.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bai.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static byte[] resize(byte[] bytes, int width, int height) {
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		try {
			Thumbnails.of(bai).size(width, height).outputQuality(0.9f).toOutputStream(bao);
			return bao.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bai.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int getSize(byte[] bytes) {
		ByteArrayInputStream bai = new ByteArrayInputStream(bytes);
		try {
			BufferedImage image = ImageIO.read(bai);
			image.getWidth();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bai.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static BufferedImage fontImage(String pressText, String fontName, int fontStyle, int fontSize, Color color) {
		Font font = new Font(fontName, fontStyle, fontSize);
		FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
		BufferedImage image = new BufferedImage(fm.stringWidth(pressText), fm.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		g.setColor(color);
		int ascent = fm.getAscent();
		g.setFont(font);
		g.drawString(pressText, 0, ascent);
		g.dispose();
		return image;
	}

	private static byte[] getByte(String file) {
		FileInputStream fileInputStream = null;
		ByteArrayOutputStream bao = null;
		try {
			fileInputStream = new FileInputStream(file);
			bao = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = fileInputStream.read(b)) != -1) {
				bao.write(b, 0, length);
			}
			return bao.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
				if (bao != null)
					bao.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void toFile(byte[] bytes, String file) {
		FileOutputStream fileOutputStream = null;
		ByteArrayInputStream bai = null;
		try {
			fileOutputStream = new FileOutputStream(file);
			bai = new ByteArrayInputStream(bytes);
			byte[] b = new byte[1024];
			int length = 0;
			while ((length = bai.read(b)) != -1) {
				fileOutputStream.write(b, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bai != null)
					bai.close();
				if (fileOutputStream != null)
					fileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] pngToJpg(MultipartFile uploadFile){
		BufferedImage bufferedImage;
		File tempDir =  FileUtils.getTempDirectory();
		File tempFile  =new File(tempDir+"/"+uploadFile.getName());
		try {
			getFile(uploadFile.getBytes(),tempDir.getPath(), uploadFile.getName());
			bufferedImage = ImageIO.read(uploadFile.getInputStream());
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0,
					Color.WHITE, null);

			ImageIO.write(newBufferedImage, "jpg",tempFile);
			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return getBytesFromFile(tempFile);
	}
	public static byte[] getBytesFromFile(File f){
		if (f == null){
			return null;
		}
		try{
			FileInputStream stream = new FileInputStream(f);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);
			stream.close();
			out.close();
			return out.toByteArray();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}

	private static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {// 判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "/" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
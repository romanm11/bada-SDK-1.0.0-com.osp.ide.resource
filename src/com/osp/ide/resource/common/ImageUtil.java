package com.osp.ide.resource.common;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;
import java.nio.charset.Charset;
import java.text.AttributedString;

import javax.swing.ImageIcon;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

import com.osp.ide.resource.Activator;

public class ImageUtil {

    public static final String DEFAULT_FONT = "default";
    //    Display.getDefault().getSystemFont().getFontData()[0].getName();
    
	public static boolean isNotENG(String input) {
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) > 0x80) {
				return true;
			}
//			UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(input
//					.charAt(i));
//			
//			byte direction = Character.getDirectionality(input.charAt(i));
//			
//			String blockName = unicodeBlock.toString();
//			System.out.println(input.charAt(i) + "] " + blockName + ", Direction : " + direction);
//			
//			if (unicodeBlock.equals(UnicodeBlock.HANGUL_JAMO)
//					|| unicodeBlock.equals(UnicodeBlock.HANGUL_SYLLABLES)
//					|| unicodeBlock.equals(UnicodeBlock.HANGUL_COMPATIBILITY_JAMO))
//
//				return true;
	 	}

		return false;
	}

	public static boolean isRightToLeft(String input) {
		for (int i = 0; i < input.length(); i++) {
			byte direction = Character.getDirectionality(input.charAt(i));
			
			System.out.println(input.charAt(i) + "] " + direction);
			
			if(direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT ||
					direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC ||
					direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_EMBEDDING ||
					direction == Character.DIRECTIONALITY_RIGHT_TO_LEFT_OVERRIDE)

				return true;
	 	}
		
		return false;
	}
	
	public static String toKorean(String str) {
		String string = null;

		try {
			if (str != null) {
				string = new String(str.getBytes(Charset.defaultCharset()),
						"KSC5601");
			}
		} catch (java.io.UnsupportedEncodingException e) {
			Activator.setErrorMessage("ImageUtil.toKorean()", e.getClass()
					+ " - " + e.getMessage(), e);
		}

		return string;
	}

	public static BufferedImage createScaledImage(BufferedImage image,
			int width, int height) {
		int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledImage = image;

		BufferedImage tempImage = new BufferedImage(width, height, type);
		Graphics2D g2 = tempImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(scaledImage, 0, 0, width, height, null);
		g2.dispose();
		g2 = null;

		scaledImage = tempImage;

		return scaledImage;
	}

	public static BufferedImage createScaledImage(int step,
			BufferedImage image, int targetWidth, int targetHeight) {
		int type = (image.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledImage = image;

		int width = image.getWidth();
		int height = image.getHeight();
		int wRatio = (targetWidth - width) / step;
		if (wRatio == 0) {
			if (targetWidth > width)
				wRatio = 1;
			else if (targetWidth < width)
				wRatio = -1;
		}
		int hRatio = (targetHeight - height) / step;
		if (hRatio == 0) {
			if (targetHeight > height)
				hRatio = 1;
			else if (targetHeight < height)
				hRatio = -1;
		}

		do {
			width += wRatio;
			if (image.getWidth() < targetWidth) {
				if (width >= targetWidth)
					width = targetWidth;
			} else if (image.getWidth() > targetWidth) {
				if (width <= targetWidth)
					width = targetWidth;
			}

			height += hRatio;
			if (image.getHeight() < targetHeight) {
				if (height >= targetHeight)
					height = targetHeight;
			} else if (image.getHeight() > targetHeight) {
				if (height <= targetHeight)
					height = targetHeight;
			}

			BufferedImage tempImage = new BufferedImage(width, height, type);
			Graphics2D g2 = tempImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2.drawImage(scaledImage, 0, 0, width, height, null);
			g2.dispose();
			g2 = null;

			scaledImage = tempImage;
		} while (targetWidth != width || targetHeight != height);

		return scaledImage;
	}

	public static BufferedImage rotate90CW(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		int type = bi.getType();
		if (type == 0)
			type = BufferedImage.TYPE_INT_RGB;

		BufferedImage biFlip = new BufferedImage(height, width, type);

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				biFlip.setRGB(height - 1 - j, i, bi.getRGB(i, j));

		return biFlip;
	}

	public static BufferedImage rotate90CCW(BufferedImage bi) {
		int width = bi.getWidth();
		int height = bi.getHeight();
		int type = bi.getType();
		if (type == 0)
			type = BufferedImage.TYPE_INT_RGB;

		BufferedImage biFlip = new BufferedImage(height, width, type);

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				biFlip.setRGB(j, width - 1 - i, bi.getRGB(i, j));

		return biFlip;
	}

	public static BufferedImage drawVStringAWT(BufferedImage bufferedImage,
			Color fontColor, String text, Font font, int hAlign, int vAlign,
			int margin) {
		bufferedImage = rotate90CCW(bufferedImage);
		Point size = new Point(bufferedImage.getWidth(), bufferedImage
				.getHeight());

		FontData fontdata = font.getFontData()[0];
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		java.awt.Font awtFont = new java.awt.Font(fontdata.getName(), fontdata
				.getStyle(), fontdata.getHeight());

		int fontWidth = g2.getFontMetrics(awtFont).stringWidth(text);
		int fontHeight = g2.getFontMetrics(awtFont).getAscent();

		Point position = new Point();

		switch (vAlign) {
		case SWT.TOP:
			position.x = margin;
			break;
		case SWT.CENTER:
			position.x = (size.x - fontWidth) / 2;
			break;
		case SWT.BOTTOM:
			position.x = size.x - fontWidth - margin;
			break;
		default:
			position.x = (size.x - fontWidth) / 2;
		}

		switch (hAlign) {
		case SWT.RIGHT:
			position.y = fontHeight;
			break;
		case SWT.CENTER:
			position.y = (size.y + fontHeight) / 2;
			break;
		case SWT.LEFT:
			position.y = size.y - margin;
			break;
		default:
			position.y = (size.y + fontHeight) / 2;
		}

		AttributedString attStr = new AttributedString(text);
		attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
				fontColor.getRed(), fontColor.getGreen(), fontColor.getBlue()));
		attStr.addAttribute(TextAttribute.FONT, awtFont);
		g2.drawString(attStr.getIterator(), position.x, position.y);
		g2.setColor(java.awt.Color.black);
		g2.dispose();
		g2 = null;

		bufferedImage = rotate90CW(bufferedImage);
		return bufferedImage;
	}

	public static BufferedImage drawStringAWT(BufferedImage bufferedImage,
			Color fontColor, String text, Font font, int hAlign, int vAlign,
			int margin) {
		FontData fontdata = font.getFontData()[0];
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Point size = new Point(bufferedImage.getWidth(), bufferedImage
				.getHeight());
		Point position = new Point();

        java.awt.Font awtFont;
        if (isNotENG(text)) {
            awtFont = new java.awt.Font(DEFAULT_FONT, fontdata
                .getStyle(), fontdata.getHeight());
        } else {
            awtFont = new java.awt.Font(fontdata.getName(), fontdata
                .getStyle(), fontdata.getHeight());
        }
        
		text = getDrawText(g2.getFontMetrics(awtFont), size.x - margin, text);

		AttributedString attStr = new AttributedString(text);
		attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
				fontColor.getRed(), fontColor.getGreen(), fontColor
						.getBlue()));
		attStr.addAttribute(TextAttribute.FONT, awtFont);

		int fontWidth = g2.getFontMetrics(awtFont).stringWidth(text);
		int fontHeight = g2.getFontMetrics(awtFont).getAscent();

		switch (hAlign) {
		case SWT.LEFT:
			position.x = margin;
			break;
		case SWT.CENTER:
			position.x = (size.x - fontWidth) / 2;
			break;
		case SWT.RIGHT:
			position.x = size.x - fontWidth - margin;
			break;
		default:
			position.x = (size.x - fontWidth) / 2;
		}

		switch (vAlign) {
		case SWT.TOP:
			position.y = fontHeight;
			break;
		case SWT.CENTER:
			position.y = (size.y + fontHeight - margin) / 2;
			break;
		case SWT.BOTTOM:
			position.y = size.y - margin;
			break;
		default:
			position.y = (size.y - fontHeight) / 2;
		}

		attStr.addAttribute(TextAttribute.WIDTH,
				TextAttribute.WIDTH_SEMI_CONDENSED);
		g2.drawString(attStr.getIterator(), position.x, position.y);
		g2.setColor(java.awt.Color.black);
		// g2.drawRect(position.x, position.y, fontWidth, fontHeight);
		g2.dispose();
		g2 = null;

		return bufferedImage;
	}

	private static String getDrawText(FontMetrics fontMetrics, int width,
			String text) {
		StringBuilder result;

		int index = text.indexOf("\\n");
		if (fontMetrics.stringWidth(text) < width && index < 0)
			return text;
		else if (index >= 0) {
			text = text.substring(0, index);
		}

		result = new StringBuilder("...");
		int offset = 0;
		while (fontMetrics.stringWidth(result.toString()) < width
				&& offset < text.length()) {
			result.insert(offset, text.charAt(offset++));
		}

		if (offset > 0 && index < 0)
			result.deleteCharAt(offset - 1);
		return result.toString();
	}
	
	public static BufferedImage drawStringAWT(BufferedImage bufferedImage,
			Color fontColor, String text, Font font, int hAlign, int vAlign,
			int xmargin, int ymargin) {
		FontData fontdata = font.getFontData()[0];
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		Point size = new Point(bufferedImage.getWidth(), bufferedImage
				.getHeight());
		Point position = new Point();

        java.awt.Font awtFont;
        if (isNotENG(text)) {
            awtFont = new java.awt.Font(DEFAULT_FONT, fontdata
                .getStyle(), fontdata.getHeight());
        } else {
            awtFont = new java.awt.Font(fontdata.getName(), fontdata
                .getStyle(), fontdata.getHeight());
        }
        
		text = getDrawText(g2.getFontMetrics(awtFont), size.x - xmargin, text);

		AttributedString attStr = new AttributedString(text);
        attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
                fontColor.getRed(), fontColor.getGreen(), fontColor
                        .getBlue()));
        attStr.addAttribute(TextAttribute.FONT, awtFont);

		int fontWidth = g2.getFontMetrics(awtFont).stringWidth(text);
		int fontHeight = g2.getFontMetrics(awtFont).getAscent();

		switch (hAlign) {
		case SWT.LEFT:
			position.x = xmargin;
			break;
		case SWT.CENTER:
			position.x = (size.x - fontWidth) / 2;
			break;
		case SWT.RIGHT:
			position.x = size.x - fontWidth - xmargin;
			break;
		default:
			position.x = (size.x - fontWidth) / 2;
		}

		switch (vAlign) {
		case SWT.TOP:
			position.y = fontHeight + ymargin;
			break;
		case SWT.CENTER:
			position.y = (size.y + fontHeight) / 2;
			break;
		case SWT.BOTTOM:
			position.y = size.y - ymargin;
			break;
		default:
			position.y = (size.y + fontHeight) / 2;
		}

		g2.drawString(attStr.getIterator(), position.x, position.y);
		g2.setColor(java.awt.Color.black);
		// g2.drawRect(position.x, position.y, fontWidth, fontHeight);
		g2.dispose();
		g2 = null;

		return bufferedImage;
	}

	public static BufferedImage drawStringAWT(BufferedImage bufferedImage,
			Color fontColor, String text, Font font, int pointX, int pointY) {
		FontData fontdata = font.getFontData()[0];
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        java.awt.Font awtFont;
        if (isNotENG(text)) {
            awtFont = new java.awt.Font(DEFAULT_FONT, fontdata
                .getStyle(), fontdata.getHeight());
        } else {
            awtFont = new java.awt.Font(fontdata.getName(), fontdata
                .getStyle(), fontdata.getHeight());
        }
        
		Point size = new Point(bufferedImage.getWidth(), bufferedImage
				.getHeight());
		text = getDrawText(g2.getFontMetrics(awtFont), size.x - pointX, text);

		AttributedString attStr = new AttributedString(text);
        attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
                fontColor.getRed(), fontColor.getGreen(), fontColor
                        .getBlue()));
        attStr.addAttribute(TextAttribute.FONT, awtFont);

		int fontHeight = g2.getFontMetrics(awtFont).getAscent();
		Point position = new Point(pointX, pointY + fontHeight);

		g2.drawString(attStr.getIterator(), position.x, position.y);
		g2.setColor(java.awt.Color.black);
		// g2.drawRect(position.x, position.y, fontWidth, fontHeight);
		g2.dispose();
		g2 = null;

		return bufferedImage;
	}
	
	public static org.eclipse.swt.graphics.Point getTextSize(String text, Font font) {
	    
	    org.eclipse.swt.graphics.Point retPoint = new org.eclipse.swt.graphics.Point(0, 0);
	    BufferedImage bufferedImage = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_ARGB);
        FontData fontdata = font.getFontData()[0];
	    Graphics2D g2 = bufferedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    
        java.awt.Font awtFont = new java.awt.Font(fontdata.getName(), fontdata
                .getStyle(), fontdata.getHeight());
	    FontMetrics fontMetrics = g2.getFontMetrics(awtFont);
	    retPoint.x = fontMetrics.stringWidth(text);	    
	    retPoint.y = fontMetrics.getHeight();
	    
	    return retPoint;
	}

	public static BufferedImage drawMultiStringAWT(BufferedImage bufferedImage,
			Color fontColor, String text, Font font, int widthMargin,
			int heightMargin) {
		FontData fontdata = font.getFontData()[0];
		Dimension size = new Dimension(bufferedImage.getWidth(), bufferedImage
				.getHeight());
		Graphics2D g2 = bufferedImage.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		java.awt.Font awtFont = new java.awt.Font(fontdata.getName(), fontdata
				.getStyle(), fontdata.getHeight());
        AttributedString attStr = null;
        if (isNotENG(text)) {
            attStr = new AttributedString(text);
            attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
                    fontColor.getRed(), fontColor.getGreen(), fontColor
                            .getBlue()));
            awtFont = new java.awt.Font(DEFAULT_FONT, fontdata.getStyle(),
                    fontdata.getHeight());
            attStr.addAttribute(TextAttribute.FONT, awtFont);
        }

        if (attStr == null) {
            attStr = new AttributedString(text);
            attStr.addAttribute(TextAttribute.FOREGROUND, new java.awt.Color(
                    fontColor.getRed(), fontColor.getGreen(), fontColor
                            .getBlue()));
            attStr.addAttribute(TextAttribute.FONT, awtFont);
        }

		FontMetrics fontMetircs = g2.getFontMetrics(awtFont);
		int fontHeight = fontMetircs.getAscent();

		Point position = new Point(size.width + widthMargin, heightMargin
				+ fontHeight);

		g2.drawString(attStr.getIterator(), position.x, position.y);

		g2.setColor(java.awt.Color.black);
		// g2.drawRect(position.x, position.y, fontWidth, fontHeight);
		g2.dispose();
		g2 = null;

		return bufferedImage;
	}

	public static BufferedImage convertToAWT(ImageData data, Color bgColor,
			Color fontColor, String titleText, Font font) {
		ColorModel colorModel = null;
		BufferedImage bufferedImage = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask,
					palette.greenMask, palette.blueMask);
			bufferedImage = new BufferedImage(colorModel, colorModel
					.createCompatibleWritableRaster(data.width, data.height),
					false, null);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					if (pixel == -256) {
						RGB rgb = bgColor.getRGB();
						bufferedImage.setRGB(x, y, rgb.red << 16
								| rgb.green << 8 | rgb.blue);
						continue;
					}
					RGB rgb = palette.getRGB(pixel);
					bufferedImage.setRGB(x, y, rgb.red << 16 | rgb.green << 8
							| rgb.blue);
				}
			}
		} else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte) rgb.red;
				green[i] = (byte) rgb.green;
				blue[i] = (byte) rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue, data.transparentPixel);
			} else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue);
			}
			bufferedImage = new BufferedImage(colorModel, colorModel
					.createCompatibleWritableRaster(data.width, data.height),
					false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}
		}

		return drawStringAWT(bufferedImage, fontColor, titleText, font,
				SWT.CENTER, SWT.TOP, 10);
	}

	public static BufferedImage convertToAWT(ImageData data, Color dfltColor) {
		ColorModel colorModel = null;
		PaletteData palette = data.palette;
		if (palette.isDirect) {
			colorModel = new DirectColorModel(data.depth, palette.redMask,
					palette.greenMask, palette.blueMask);
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					if (pixel == -256) {
						RGB rgb = dfltColor.getRGB();
						bufferedImage.setRGB(x, y, rgb.red << 16
								| rgb.green << 8 | rgb.blue);
						continue;
					}
					RGB rgb = palette.getRGB(pixel);
					bufferedImage.setRGB(x, y, rgb.red << 16 | rgb.green << 8
							| rgb.blue);
				}
			}
			return bufferedImage;
		} else {
			RGB[] rgbs = palette.getRGBs();
			byte[] red = new byte[rgbs.length];
			byte[] green = new byte[rgbs.length];
			byte[] blue = new byte[rgbs.length];
			for (int i = 0; i < rgbs.length; i++) {
				RGB rgb = rgbs[i];
				red[i] = (byte) rgb.red;
				green[i] = (byte) rgb.green;
				blue[i] = (byte) rgb.blue;
			}
			if (data.transparentPixel != -1) {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue, data.transparentPixel);
			} else {
				colorModel = new IndexColorModel(data.depth, rgbs.length, red,
						green, blue);
			}
			BufferedImage bufferedImage = new BufferedImage(colorModel,
					colorModel.createCompatibleWritableRaster(data.width,
							data.height), false, null);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int pixel = data.getPixel(x, y);
					pixelArray[0] = pixel;
					raster.setPixel(x, y, pixelArray);
				}
			}
			return bufferedImage;
		}
	}

	public static ImageData convertToSWT(java.awt.Image input) {
		if (input instanceof BufferedImage)
			return convertToSWT((BufferedImage) input);
		if (input instanceof VolatileImage)
			return convertToSWT(((VolatileImage) input).getSnapshot());

		// too lazy to use MediaTracker directly:
		ImageIcon imageIcon = new ImageIcon(input);
		if (imageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
			throw new IllegalArgumentException("Can't load image");

		input = imageIcon.getImage();
		int w = input.getWidth(null);
		int h = input.getHeight(null);

		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();
		BufferedImage output = gc.createCompatibleImage(w, h);

		// What about transparency? This code assumes it's OPAQUE,
		// but you can interrogate the image's source for its ColorModel
		Graphics2D g = output.createGraphics();
		g.drawImage(input, 0, 0, null);
		g.dispose();
		g = null;
		return convertToSWT(output);
	}

	public static ImageData convertToSWT(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {
			DirectColorModel colorModel = (DirectColorModel) bufferedImage
					.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(),
					colorModel.getGreenMask(), colorModel.getBlueMask());
			ImageData data = new ImageData(bufferedImage.getWidth(),
					bufferedImage.getHeight(), colorModel.getPixelSize(),
					palette);
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					int rgb = bufferedImage.getRGB(x, y);
					int pixel = palette.getPixel(new RGB((rgb >> 16) & 0xFF,
							(rgb >> 8) & 0xFF, rgb & 0xFF));
					data.setPixel(x, y, pixel);
					if (colorModel.hasAlpha()) {
						data.setAlpha(x, y, (rgb >> 24) & 0xFF);
					}
				}
			}
			return data;
		} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
			IndexColorModel colorModel = (IndexColorModel) bufferedImage
					.getColorModel();
			int size = colorModel.getMapSize();
			byte[] reds = new byte[size];
			byte[] greens = new byte[size];
			byte[] blues = new byte[size];
			colorModel.getReds(reds);
			colorModel.getGreens(greens);
			colorModel.getBlues(blues);
			RGB[] rgbs = new RGB[size];
			for (int i = 0; i < rgbs.length; i++) {
				rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
						blues[i] & 0xFF);
			}
			PaletteData palette = new PaletteData(rgbs);
			ImageData data = new ImageData(bufferedImage.getWidth(),
					bufferedImage.getHeight(), colorModel.getPixelSize(),
					palette);
			data.transparentPixel = colorModel.getTransparentPixel();
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					data.setPixel(x, y, pixelArray[0]);
				}
			}
			return data;
		}
		return null;
	}

}

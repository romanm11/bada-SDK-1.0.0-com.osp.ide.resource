package com.osp.ide.resource.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import com.osp.ide.resource.Activator;

public class FileUtil {

	public static String readString(InputStream is) {
		if (is == null)
			return null;
		BufferedReader reader = null;
		try {
			StringBuffer buffer = new StringBuffer();
			char[] part = new char[2048];
			int read = 0;
			reader = new BufferedReader(new InputStreamReader(is, Charset
					.defaultCharset())); //$NON-NLS-1$

			while ((read = reader.read(part)) != -1)
				buffer.append(part, 0, read);

			return buffer.toString();

		} catch (IOException ex) {
			Activator.setErrorMessage("FileUtil.readString()", ex.getClass()
					+ " - " + ex.getMessage(), ex);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ex) {
					Activator.setErrorMessage("FileUtil.readString()",
							"finally] " + ex.getClass() + " - "
									+ ex.getMessage(), ex);
				}
			}
		}
		return null;
	}

	public static boolean copyFile(String sourceFile, String distPath) {
		File srcFile = new File(sourceFile);
		if (!srcFile.exists())
			return false;

		File distFile = new File(distPath + File.separator + srcFile.getName());
		if (distFile.exists())
			return false;

		return copyFile_internal(srcFile, distFile);
	}

	public static boolean copyAsFile(String sourceFile, String distPath) {
		File srcFile = new File(sourceFile);
		if (!srcFile.exists())
			return false;

		File distFile = new File(distPath);

		return copyFile_internal(srcFile, distFile);
	}

	private static boolean copyFile_internal(File srouceFile,
			File destinationFile) {
		FileInputStream input = null;
		FileChannel sourceChannel = null;
		FileOutputStream output = null;
		FileChannel destinationChannel = null;
		try {
			input = new FileInputStream(srouceFile);
			sourceChannel = input.getChannel();

			output = new FileOutputStream(destinationFile);
			destinationChannel = output.getChannel();
			destinationChannel.transferFrom(sourceChannel, 0, sourceChannel
					.size());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (sourceChannel != null)
					sourceChannel.close();
				if (input != null)
					input.close();
				if (destinationChannel != null)
					destinationChannel.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				Activator.setErrorMessage("FileUtil.copyFile_internal()", e
						.getClass()
						+ " - " + e.getMessage(), e);
			}
		}
		return true;
	}
}

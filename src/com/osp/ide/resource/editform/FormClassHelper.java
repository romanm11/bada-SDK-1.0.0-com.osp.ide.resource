package com.osp.ide.resource.editform;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

import com.osp.ide.resource.Activator;

public class FormClassHelper {
	public static final String START_PATTERN = "$("; //$NON-NLS-1$
	public static final String END_PATTERN = ")"; //$NON-NLS-1$

	public static String readFromFile(URL source) {
		char[] chars = new char[4092];
		InputStreamReader contentsReader = null;
		StringBuffer buffer = new StringBuffer();
		if (!new java.io.File(source.getFile()).exists()) {
			return "";
		} else {
			try {
				contentsReader = new InputStreamReader(source.openStream(),
						Charset.defaultCharset());

				int c;
				do {
					c = contentsReader.read(chars);
					if (c == -1)
						break;
					buffer.append(chars, 0, c);
				} while (c != -1);

			} catch (IOException e) {
				return "";
			} finally {
				try {
					if (contentsReader != null)
						contentsReader.close();
				} catch (IOException e) {
					Activator.setErrorMessage("FormClassHelper.readFromFile()",
							e.getClass() + " - " + e.getMessage(), e);
				}
			}

		}
		return buffer.toString();
	}

	public static Set<String> getReplaceKeys(String str) {
		Set<String> replaceStrings = new HashSet<String>();
		int start = 0, end = 0;
		while ((start = str.indexOf(START_PATTERN, start)) >= 0) {
			end = str.indexOf(END_PATTERN, start);
			if (end != -1) {
				replaceStrings.add(str.substring(
						start + START_PATTERN.length(), end));
				start = end + END_PATTERN.length();
			} else
				start++;
		}
		return replaceStrings;
	}

	public static String getValueAfterExpandingMacros(String string,
			Set<String> macros, Map<String, String> valueStore) {
		StringBuilder s;
		for (Iterator<String> i = macros.iterator(); i.hasNext();) {
			String key = i.next();
			String value = valueStore.get(key);
			if (value != null) {
				s = new StringBuilder(START_PATTERN);
				s.append(key);
				s.append(END_PATTERN);
				string = string.replace(s.toString(), value);
			}
		}
		return string;
	}

	public static boolean copyBinaryFile(URL source, File dest) {
		byte[] bytes = new byte[4092];
		if (source != null && dest != null) {
			File file = new File(source.getFile());
			if (file.isFile()) {
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(file);
					fos = new FileOutputStream(dest);
					int ch;
					while (true) {
						ch = fis.read(bytes);
						if (ch == -1) {
							break;
						}
						fos.write(bytes, 0, ch);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				} finally {
					try {
						if (fis != null)
							fis.close();
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						Activator.setErrorMessage(
								"FormClassHelper.copyBinaryFile()", e
										.getClass()
										+ " - " + e.getMessage(), e);
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	public static boolean copyFileToProject(IProject prj, String sourcePath,
			String distName, boolean refresh) {
		IPath prjPath = prj.getLocation();
		prjPath = prjPath.append(distName);

		File file = prjPath.makeAbsolute().toFile();

		File srcFile = new File(sourcePath);
		if (!srcFile.exists())
			return false;

		if (copyBinaryFile2(srcFile, file)) {
			if (refresh) {
				IFile fi = prj.getFile(distName);
				try {
					fi.refreshLocal(IResource.DEPTH_ONE, null);
				} catch (CoreException e) {
					Activator.setErrorMessage(
							"FormClassHelper.copyFileToProject()", e.getClass()
									+ " - " + e.getMessage(), e);
				}
			}

		}

		return true;

	}

	public static boolean createFile(IProject prj, String text, String distName) {
		IPath prjPath = prj.getLocation();
		prjPath = prjPath.append(distName);

		File dest = prjPath.makeAbsolute().toFile();

		if (dest != null) {
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(dest)));
				bw.write(text);
			} catch (IOException e2) {
				return false;
			} finally {
				try {
					if (bw != null)
						bw.close();
				} catch (IOException e) {
					Activator.setErrorMessage("FormClassHelper.createFile()", e
							.getClass()
							+ " - " + e.getMessage(), e);
				}
			}

		} else {
			return false;
		}

		IFile fi = prj.getFile(distName);
		try {
			fi.refreshLocal(IResource.DEPTH_ONE, null);
		} catch (CoreException e) {
			Activator.setErrorMessage("FormClassHelper.createFile()", e
					.getClass()
					+ " - " + e.getMessage(), e);
		}

		return true;

	}

	public static boolean copyBinaryFile2(File source, File dest) {
		byte[] bytes = new byte[4092];
		if (source != null && dest != null) {
			if (source.isFile()) {
				FileInputStream fis = null;
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream(source);
					fos = new FileOutputStream(dest);
					int ch;
					while (true) {
						ch = fis.read(bytes);
						if (ch == -1) {
							break;
						}
						fos.write(bytes, 0, ch);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				} finally {
					try {
						if (fis != null)
							fis.close();
						if (fos != null)
							fos.close();
					} catch (IOException e) {
						Activator.setErrorMessage(
								"FormClassHelper.copyBinaryFile2()", e
										.getClass()
										+ " - " + e.getMessage(), e);
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}

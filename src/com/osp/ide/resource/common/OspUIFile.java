package com.osp.ide.resource.common;

import java.io.File;
import java.util.Locale;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.osp.ide.resource.resinfo.RefString;

public class OspUIFile {
	private String m_pathname;

	public boolean CreateDir(String dir) {
		String s;
		File fDir;

		if (dir.charAt(dir.length() - 1) != java.io.File.separatorChar)
			dir += java.io.File.separatorChar;

		// System.out.println("Dir : "+dir);
		int j = dir.indexOf(":\\") + 2;
		int i;

	// TODO implement
		while ((i = dir.indexOf('\\', j)) > -1) {
			s = dir.substring(0, i);
			// System.out.println("Create Dir : "+s+", j : "+j+", i : "+i);
			fDir = new File(s);
			if (!IsFileExist(s)) {
				if (!fDir.mkdir())
					return false;
			}
			j = i + 1;
		}
		return true;
	};

	public boolean DeleteDir(File dir) {
		File fileAll[];
		fileAll = dir.listFiles();
		if (fileAll == null)
			dir.delete();
		else {
			for (int i = 0; i < fileAll.length; i++) {
				if (fileAll[i].isDirectory())
					DeleteDir(fileAll[i]);
				else
					fileAll[i].delete();
			}
		}
		return true;
	};

	public boolean IsValidName(RefString name, boolean file) {
		name.s = name.s.trim();

		String s, s1;
		int i;

		StringBuilder sBuilder;
		if (file == true) {
			s = "\\/:*?\"<>|";
			for (i = 0; i < s.length(); i++) {
				if (name.s.indexOf(s.charAt(i)) != -1) {
					sBuilder = new StringBuilder("Invalid characters in \'");
					sBuilder.append(s.charAt(i));
					sBuilder.append("\'");
					System.out.println(sBuilder.toString());
					return false;
				}
			}

			i = name.s.lastIndexOf('.');
			if (i > -1) {
				// extension
				s = name.s.substring(i + 1);
				s = s.trim();

				// file title
				s1 = name.s.substring(0, i - 1);
				s1 = s1.trim();

				name.s = s1 + '.' + s;
			}
		} // file
		else // dir
		{
			i = name.s.indexOf(":\\");
			if (i < 0) // drive name is not exist
			{
				// get current directory
				String buf;

				if ((buf = System.getProperty("user.dir")).isEmpty())
					return false;
				s1 = buf + '\\';
			} else {
				// get drive list
				File[] lpDriveStrings = File.listRoots();
				
				if(lpDriveStrings == null)
					return false;

				int dwCount = lpDriveStrings.length;
				if (dwCount == 0)
					return false;

				// get drive name
				s = name.s.substring(0, i);
				s = s.trim();
				s = s.toUpperCase(Locale.getDefault());

				if (s.length() != 1) {
					System.out.println("Invalid drive name");
					return false;
				}
				int j;
				for (j = 0; j < lpDriveStrings.length; j++) {
					// System.out.println("Drive : "+s+", Sys : "+lpDriveStrings[j].getAbsolutePath().charAt(0));
					if (lpDriveStrings[j].getAbsolutePath().charAt(0) == s
							.charAt(0))
						break;
				}
				if (j >= lpDriveStrings.length) {
					System.out.println("Invalid drive name");
					return false;
				}

				s1 = s + ":\\"; // drive
				s1 = s1.toUpperCase(Locale.getDefault());
				name.s = name.s.substring(i + 2); // exclude drive name
			}

			s = "/:*?\"<>|";
			if (name.s.indexOf("\\\\") > -1) {
				System.out.println("Invalid characters\r\n \\\\");
				return false;
			}
			for (i = 0; i < s.length(); i++) {
				if (name.s.indexOf(s.charAt(i)) != -1) {
					sBuilder = new StringBuilder("Invalid characters in \'");
					sBuilder.append(s.charAt(i));
					sBuilder.append("\'");
					System.out.println(sBuilder.toString());
					return false;
				}
			}

			// get folder names
			name.s += '\\';
			while ((i = name.s.indexOf('\\')) > -1) {
				s = name.s.substring(0, i);
				s = s.trim();
				name.s = name.s.substring(i + 1);

				if (s.length() != 0) {
					sBuilder = new StringBuilder(s1);
					sBuilder.append(s);
					sBuilder.append('\\');
					s1 = sBuilder.toString();
				}
			}

			s1 = s1.substring(0, s1.length() - 1);
			name.s = s1;
		} // dir
		return true;
	};

	public void SetFilePath(String pathname) {
		IPath path = Path.fromPortableString(pathname);
		pathname = path.toOSString();
		
		m_pathname = pathname;
	};

	public String GetFilePath() {
		return m_pathname;
	};

	public String GetFileName(String pathname) {
		String s;
		if (pathname.length() != 0)
			s = pathname;
		else
			s = m_pathname;

		IPath path = Path.fromPortableString(s);
		s = path.toOSString();
		// filename
		int i = s.lastIndexOf('\\');
		if (i < 0)
			i = s.lastIndexOf('/');
		if (i > -1)
			s = s.substring(i + 1);

		return s;
	};

	public String GetFileExt(String pathname) {
		String s;
		if (pathname.length() != 0)
			s = pathname;
		else
			s = m_pathname;

		int i = s.lastIndexOf('.');
		if (i > -1)
			s = s.substring(i + 1);

		return s;
	};

	public String GetFileTitle(String pathname) {
		String s;
		if (pathname.length() != 0)
			s = pathname;
		else
			s = m_pathname;

		IPath path = Path.fromPortableString(s);
		s = path.toOSString();
		// filename
		int i = s.lastIndexOf('\\');
		if (i < 0)
			i = s.lastIndexOf('/');
		if (i > -1)
			s = s.substring(i + 1);

		// extension
		i = s.lastIndexOf('.');
		if (i > -1)
			s = s.substring(0, i);

		return s;
	};

	public String GetDir(String pathname) {
		String s;
		if (pathname.isEmpty())
			s = m_pathname;
		else
			s = pathname;
		
		IPath path = Path.fromPortableString(s);
		s = path.toOSString();

		int i = s.lastIndexOf('\\');
		if (i == -1)
			i = s.lastIndexOf('/');
		if (i == -1)
			return "";
		return s.substring(0, i);
	};

	public void MakeFileName(RefString pathname) {
		int i = 0;

		String ext = GetFileExt(pathname.s);
		String dir = GetDir(pathname.s);
		String title = GetFileTitle(pathname.s);

		if (IsFileExist(pathname.s))
			do {
				pathname.s = String.format("%s" + java.io.File.separatorChar + "%s%d.%s", dir, title, i++, ext);
			} while (IsFileExist(pathname.s));
	};

	public String MakeFileName(String pathname) {
		int i = 0;
		String retString;

		String ext = GetFileExt(pathname);
		String dir = GetDir(pathname);
		String title = GetFileTitle(pathname);
		retString = pathname;

		if (IsFileExist(pathname))
			do {
				retString = String.format("%s/%s%d.%s", dir, title, i++, ext);
			} while (IsFileExist(retString));

		return retString;
	};

	public boolean IsFileExist(String pathname) {
		File file = new File(pathname);
		// System.out.println(pathname+" File Exists : "+file.exists());
		return file.exists();
	}

	public String FormatFilename(String filename, String ext) {
		String s = GetFileTitle(filename) + '.' + ext;
		return s;
	};

	public OspUIFile() {
		;
	}
}
